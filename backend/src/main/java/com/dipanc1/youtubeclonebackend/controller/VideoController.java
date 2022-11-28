package com.dipanc1.youtubeclonebackend.controller;


import com.dipanc1.youtubeclonebackend.dto.CommentDto;
import com.dipanc1.youtubeclonebackend.dto.UploadVideoResponse;
import com.dipanc1.youtubeclonebackend.dto.VideoDto;
import com.dipanc1.youtubeclonebackend.model.Comment;
import com.dipanc1.youtubeclonebackend.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UploadVideoResponse uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        return videoService.uploadVideo(file);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file, @RequestParam("videoId") String videoId) throws IOException {
        return videoService.uploadThumbnail(file, videoId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VideoDto editVideoMetaData(@RequestBody VideoDto videoDto){
        return videoService.editVideo(videoDto);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto getVideoMetaData(@PathVariable String videoId){
        return videoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto likeVideo(@PathVariable String videoId){
        return videoService.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoDto disLikeVideo(@PathVariable String videoId){
        return videoService.disLikeVideo(videoId);
    }


    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addComment (@PathVariable String videoId, @RequestBody CommentDto commentDto){
        videoService.addComment(videoId, commentDto);
    }

    @GetMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable String videoId) {
        return videoService.getAllComments(videoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> getAllVideos(){
        return videoService.getAllVideos();
    }


}
