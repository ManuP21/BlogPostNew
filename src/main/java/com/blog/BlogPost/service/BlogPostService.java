package com.blog.BlogPost.service;

import com.blog.BlogPost.model.BlogPost;
import com.blog.BlogPost.model.History;
import com.blog.BlogPost.repository.BlogPostRepository;
import com.blog.BlogPost.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    HistoryRepository historyRepository;

    public List<BlogPost> getAllPost(){
       return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getPostById(long id){
       return blogPostRepository.findById(id);
    }

    @Transactional
    public BlogPost saveBlogPost(BlogPost blogPost){
      return blogPostRepository.saveAndFlush(blogPost);
    }

    public void deleteBlogById (long id){
        blogPostRepository.deleteById(id);
    }

    @Transactional
    public BlogPost updatePost(BlogPost blogPost){
        BlogPost blogPostNew = new BlogPost();
        BlogPost blogPostOld = new BlogPost();
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(blogPost.getId());
        saveToHistory(blogPostOptional.get());
        if (blogPostOptional.isPresent()){
            blogPostOld  = blogPostOptional.get();
            blogPostOld.setBody(blogPost.getBody());
            blogPostOld.setTitle(blogPost.getTitle());
            blogPostOld.setTags(blogPost.getTags());
            blogPostNew = blogPostRepository.save(blogPostOld);
        }
            return blogPostNew;
    }

    private void saveToHistory(BlogPost oldPost ){
        List<History> historyList = new ArrayList<>();

            History history = new History();
            history.setId(oldPost.getId());
            history.setBody(oldPost.getBody());
            history.setTitle(oldPost.getTitle());
            history.setTags(oldPost.getTags());
            historyList.add(history);
            historyRepository.saveAll(historyList);

    }
    public List<History> getBlogsHistoryInRange(long firstVersion ,long secVersion){
       return historyRepository.findByHistoryIdBetween(firstVersion,secVersion);
    }
}
