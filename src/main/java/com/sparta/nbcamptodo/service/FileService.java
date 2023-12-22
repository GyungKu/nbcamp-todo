package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.TodoImage;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    TodoImage upload(MultipartFile multipartFile, Todo todo) throws IOException;

}
