package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.TodoImage;
import com.sparta.nbcamptodo.repository.TodoImageRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService{

    private final S3UploadService s3UploadService;
    private final TodoImageRepository todoImageRepository;

    /**
     * 같은 이름의 파일을 저장하면 덮어 씌워지기 때문에 uuid로 저장하는 이름은 따로 설정
     * 그러나 반환할 때 저장한 이름이 필요할 수도 있어서 저장한 이름도 같이 저장
     */
    @Override
    public TodoImage upload(MultipartFile multipartFile, Todo todo) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String saveName = UUID.randomUUID().toString();
        String url = s3UploadService.saveFile(multipartFile, saveName);
        TodoImage todoImage = new TodoImage(originName, UUID.randomUUID().toString(), url, todo);
        todoImageRepository.save(todoImage);
        todo.addImage(todoImage);
        return todoImage;
    }
}
