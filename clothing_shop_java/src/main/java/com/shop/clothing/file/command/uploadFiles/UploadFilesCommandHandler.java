package com.shop.clothing.file.command.uploadFiles;

import com.shop.clothing.common.Cqrs.HandleResponse;
import com.shop.clothing.common.Cqrs.IRequestHandler;
import com.shop.clothing.file.IFileService;
import com.shop.clothing.file.command.uploadFile.UploadFileCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UploadFilesCommandHandler implements IRequestHandler<UploadFilesCommand, Collection<String>> {
    private final IFileService fileService;

    @Override
    public HandleResponse<Collection<String>> handle(UploadFilesCommand uploadFileCommand) {
//        Arrays.stream(uploadFileCommand.getFiles()).forEach(file -> {
//
//            try {
//                var fileName = fileService.uploadFile(file);
//            } catch (Exception e) {
//            }
//        });
        // can i map to a promise like javascript
        // - yes, you can use CompletableFuture
        // how can i handle exceptions
        // - you can use CompletableFuture::exceptionally
        // -
        // - https://www.baeldung.com/java-completablefuture

        try {
            List<CompletableFuture<String>> futures = Arrays.stream(uploadFileCommand.getFiles())
                    .map(file -> CompletableFuture.supplyAsync(() -> fileService.uploadFile(file)))
                    .toList();
            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
            var result = futures.stream().map(CompletableFuture::join).toList();
            return HandleResponse.ok(result);
        } catch (Exception e) {
            return HandleResponse.error(e.getMessage());
        }


    }
}
