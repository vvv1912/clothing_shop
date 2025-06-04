package com.shop.clothing.file.command.uploadFile;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadFileCommand implements IRequest<String>{

  private   MultipartFile file;
}
