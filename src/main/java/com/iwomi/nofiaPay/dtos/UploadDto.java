package com.iwomi.nofiaPay.dtos;

import com.iwomi.nofiaPay.core.enums.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

public record UploadDto(
        FileTypeEnum type,
        MultipartFile file
) {
}
