package com.dipanc1.youtubeclonebackend.service;

import com.dipanc1.youtubeclonebackend.dto.CommentDto;
import com.dipanc1.youtubeclonebackend.dto.UploadVideoResponse;
import com.dipanc1.youtubeclonebackend.dto.VideoDto;
import com.dipanc1.youtubeclonebackend.model.Comment;
import com.dipanc1.youtubeclonebackend.model.Video;
import com.dipanc1.youtubeclonebackend.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final BlobService blobService;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) throws IOException {
        String videoUrl = blobService.uploadFile(multipartFile);

        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find the video by VideoID
        var savedVideo = getVideoById(videoDto.getId());

        // Map the videoDto fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        // save the video to the db
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) throws IOException {
        var savedVideo = getVideoById(videoId);

        String thumbnailUrl = blobService.uploadFile(file);

        savedVideo.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(savedVideo);

        return thumbnailUrl;
    }

    Video getVideoById(String videoId){
        // Find the video by VideoID
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = getVideoById(videoId);

        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

        return mapToVideoDto(savedVideo);

    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {
        // get video by id
        Video videoById = getVideoById(videoId);

        // implement like count
        // if user already  liked the video, then decrement like count
        // if user already disliked the video, then increment like count and decrement dislike count

        if (userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDislikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        } else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);

    }

    private static VideoDto mapToVideoDto(Video videoById) {

        VideoDto videoDto = new VideoDto();

        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());

        return videoDto;
    }

    public VideoDto disLikeVideo(String videoId) {
        // get video by id
        Video videoById = getVideoById(videoId);

        // implement like count
        // if user already  liked the video, then decrement like count
        // if user already disliked the video, then increment like count and decrement dislike count

        if (userService.ifDislikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        } else {
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());

        video.addComment(comment);

        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();

        return commentList.stream().map(this::mapToCommentDto).toList();
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorId());
        return commentDto;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream().map(VideoService::mapToVideoDto).toList();
    }
}
