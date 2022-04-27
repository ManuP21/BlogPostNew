package com.blog.BlogPost.controller;

import com.blog.BlogPost.model.BlogPost;
import com.blog.BlogPost.model.History;
import com.blog.BlogPost.service.BlogPostService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogPostController {

    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);
    @Autowired
    BlogPostService blogPostService;

    @GetMapping(value = "/getBlogPosts")
    public ResponseEntity<List<BlogPost>> getAllPost(){
        try{
            List<BlogPost> blogPostList = new ArrayList<>();
            logger.info("Getting the details for all the posts");
            blogPostList = blogPostService.getAllPost();

           return new ResponseEntity<>(blogPostList, HttpStatus.OK);
        }catch (Exception exception){
            logger.error("Issue while fetching the posts");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getPostById")
    public ResponseEntity<Optional<BlogPost>> getPostById(@RequestParam long id){
        logger.info("Getting the details for the post for id{}",id);
            Optional<BlogPost> postOptional = blogPostService.getPostById(id);
            if (postOptional.isPresent()){
                return new ResponseEntity<>(postOptional, HttpStatus.OK);
            }else{
                logger.error("Issue while fetching the post for id{}",id);
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping(value = "/savePost")
    public ResponseEntity<BlogPost> savePost(@RequestBody BlogPost blogPost){
        try{
           BlogPost blogPost1 = new BlogPost();
            if (blogPost!= null){
                logger.info("Trying to save the details for post");
                blogPost1 = blogPostService.saveBlogPost(blogPost);
            }
            return new ResponseEntity<>(blogPost1, HttpStatus.CREATED);
        }catch (Exception exception){
            logger.error("Issue while saving the post");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deletePost")
    public ResponseEntity<HttpStatus> deletePost(@RequestParam long id){
        try{
            logger.info("Deleting the post for id{} ",id);
            blogPostService.deleteBlogById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        catch (Exception exception){
            logger.error("Issue while deleting the posts");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updatePost")
    public ResponseEntity<BlogPost> updatePost(@RequestBody BlogPost blogPost){
        BlogPost blogPost1 = new BlogPost();
        try{
            logger.info("Updating the post");
             blogPost1 = blogPostService.updatePost(blogPost);
        }catch (Exception exception){
            logger.error("Issue while updating the post");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(blogPost1, HttpStatus.CREATED);
    }


    @GetMapping(value = "/getPostsHistoryFrom")
    public ResponseEntity<List<History>> getBlogsHistoryInRange(@RequestParam long firstVersion, long secVersion){
        try{
            List<History> historyList = new ArrayList<>();
            logger.info("Getting the history details for the given range");
            historyList = blogPostService.getBlogsHistoryInRange(firstVersion,secVersion);
            return new ResponseEntity<>(historyList, HttpStatus.OK);
        }catch (Exception exception){
            logger.error("Issue while fetching the post between range");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
