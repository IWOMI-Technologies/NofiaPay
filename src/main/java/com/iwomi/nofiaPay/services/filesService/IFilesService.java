package com.iwomi.nofiaPay.services.filesService;

import com.iwomi.nofiaPay.core.enums.FileTypeEnum;
import com.iwomi.nofiaPay.dtos.UploadDto;
import org.springframework.web.multipart.MultipartFile;

public interface IFilesService {
    Boolean importFile(UploadDto dto);
//    Boolean clientUpload(FileTypeEnum type, MultipartFile file);
//    Boolean accountUpload(FileTypeEnum type, MultipartFile file);
//    Boolean accountHistoryUpload(FileTypeEnum type, MultipartFile file);
}
