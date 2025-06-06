package com.BlogApp.demo.demo.Services;


import com.BlogApp.demo.demo.Dtos.BlogDto;
import com.BlogApp.demo.demo.Entities.BlogEntity;
import com.BlogApp.demo.demo.Repositories.BlogRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.SourceType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public class BlogService {

    private BlogRepository blogRepository;
    private ModelMapper modelMapper;
    private FileService fileService;

    public BlogService(){}
    @Value("${project.image}")
    private String imagePath;

    @Autowired
    public BlogService(BlogRepository blogRepository, ModelMapper modelMapper, FileService fileService){
        this.blogRepository = blogRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    public BlogDto saveBlog(BlogDto blogDto, MultipartFile image) throws IOException {
// Upload the image
        System.out.println(imagePath + image);
        String imgpathurl = fileService.uploadFile(imagePath, image);

        // Create the URL for the uploaded image
//        String imageUrl = "http://localhost:8080/images/" + fileName;

        BlogEntity blog = modelMapper.map(blogDto, BlogEntity.class);
        blog.setImgurl(imgpathurl);


        return modelMapper.map(blogRepository.save(blog), BlogDto.class);
    }

    public String saveimg(Long id, MultipartFile image) throws IOException {
        BlogEntity blog = blogRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Blog not found with id: {}" + id));
        String imgpathurl = fileService.uploadFile(imagePath, image);
        blog.setImgurl(imgpathurl);
        blogRepository.save(blog);
        return imgpathurl;
    }

    public boolean deleteBlog(Long id) {
//        BlogEntity blog = blogRepository.findById(id).orElseThrow(() ->
//                new NoSuchElementException("Blog not found with id: " + id));

        BlogEntity blog = blogRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Blog not found with id: " + id));
        System.out.println(blog);//BlogEntity(id=103, title=SkyPainting, content=testing, imgurl=557f530a-f109-4ee2-90cc-18d537eb1e49.png)

        if(blog.getContent().isEmpty()){
            return false;
        }else {
            // Delete the image file from the server
            deleteFile(blog.getImgurl());

            // Delete the blog entry from the database
            blogRepository.delete(blog);
            return true;
        }
    }

    private void deleteFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(imagePath + File.separator + filePath);
            System.out.println(filePath); //5f3ecda0-16ae-4255-a917-c8b6ec126572.png
            System.out.println(file); // images\5f3ecda0-16ae-4255-a917-c8b6ec126572.png
            if (file.exists()) {
                file.delete();
            }
        }
    }


    public List<BlogDto> getAllBlog() {

        List<BlogEntity> blogs=blogRepository.findAll();
        List<BlogDto> blogDtos=new ArrayList<>();
        for(BlogEntity blog:blogs){
            BlogDto blogDto=modelMapper.map(blog,BlogDto.class);
            blogDtos.add(blogDto);
        }
        return  blogDtos;
    }
}
