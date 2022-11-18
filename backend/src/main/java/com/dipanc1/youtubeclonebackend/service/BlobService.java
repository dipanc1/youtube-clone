package com.dipanc1.youtubeclonebackend.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlobService implements FileService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        var key = UUID.randomUUID() + "." + file.getOriginalFilename();

        String constr = "DefaultEndpointsProtocol=https;AccountName=youtubeclone;AccountKey=5tPYwwEkL7E88g41aneddwmMFfwk53as/jQCj9P8XrLYDwD1eZQUqehUSfpmAqIZc9tbkyzooIF3+AStzSFTXQ==;EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("youtubeclone")
                .buildClient();

        BlobClient blob = container.getBlobClient(key);

        blob.upload(file.getInputStream(), file.getSize(),true);

//        it prints url of file uploaded
//        System.out.println(blob.getBlobUrl());

        return blob.getBlobUrl();
    }
}
