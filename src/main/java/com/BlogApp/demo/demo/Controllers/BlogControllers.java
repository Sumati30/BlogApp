package com.BlogApp.demo.demo.Controllers;


import com.BlogApp.demo.demo.Dtos.BlogDto;

import com.BlogApp.demo.demo.Services.BlogService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@AllArgsConstructor
@RequestMapping("/blog")
@CrossOrigin(origins = "http://localhost:4201")
public class BlogControllers {

    private BlogService blogService;

    @Autowired
    public BlogControllers(BlogService blogService){
        this.blogService = blogService;
    }

    @GetMapping("/getall")
    ResponseEntity<List<BlogDto>> getAllBlog(){

        return ResponseEntity.ok(blogService.getAllBlog());
    }

    @PostMapping("/upload")
    public ResponseEntity<BlogDto> uploadProduct(
            @RequestPart("blogDto") BlogDto blogDto,
            @RequestPart("image") MultipartFile image) throws IOException {


        BlogDto postedbBlog = blogService.saveBlog(blogDto, image);
        return ResponseEntity.ok(postedbBlog);

    }

    @PutMapping("/updateimage/{id}")
    public ResponseEntity<String> uploadimage(@PathVariable Long id,
                                              @RequestPart("image") MultipartFile image) throws IOException {


        String url = blogService.saveimg(id,image);
        return ResponseEntity.ok(url);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
//        blogService.deleteBlog(id);
//        return ResponseEntity.ok("Blog deleted successfully along with the image.");

        try {
            // logic to delete the blog and image
            boolean isDeleted = blogService.deleteBlog(id);
            System.out.println(isDeleted);
            if (!isDeleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Blog not found or could not be deleted.");
            }

            return ResponseEntity.ok("Blog deleted successfully along with the image.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the blog: " + e.getMessage());
        }
    }

}

