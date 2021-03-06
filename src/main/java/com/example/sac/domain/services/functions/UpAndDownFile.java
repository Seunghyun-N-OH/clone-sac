package com.example.sac.domain.services.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.sac.domain.entities.AttachedFile;
import com.example.sac.domain.entities.EventDetailImg;
import com.example.sac.domain.entities.EventPoster;
import com.google.gson.JsonObject;

import org.springframework.core.io.ClassPathResource; //  TODO Local
import org.springframework.web.multipart.MultipartFile;

// 주석 업데이트 220126
// 파일 관련된 작업해주는 함수 모아둔 클래스 : 서비스가 너무 길어져 보기싫어서 만들긴 했는데 여기가 길어지네

public class UpAndDownFile {

    // TODO 로컬에서 작동할 path
    private static final String p = "/uploads/notice/includingIMGs/";
    private static final ClassPathResource cpr = new ClassPathResource("static" +
            p);
    private static final String localRoot = "E:/test/sac/bin/main/static";

    // TODO ec2에서 작동할 path
    // private static final String p = "/uploads/imgs/upload/";
    // private static final String localRoot = "/home/ec2-user/src/root";

    // 업로드된 파일이 저장 될 때, 중복으로 인한 문제가 생기지 않도록 앞에 붙여줄 번호
    private static long fileNo = 10L;
    private static long fileNo_sub = Integer.toUnsignedLong(LocalDateTime.now().getSecond())
            + Integer.toUnsignedLong(LocalDateTime.now().getNano());

    // 공지사항 등록 시 파일 업로드할 때
    public static List<AttachedFile> uploadMultipleFiles(List<MultipartFile> a) {
        List<AttachedFile> c = new ArrayList<>();
        for (MultipartFile b : a) {
            try {
                if (!b.getOriginalFilename().isEmpty()) {
                    System.out.println(Integer.toUnsignedLong(LocalDateTime.now().getSecond()) + "+"
                            + Integer.toUnsignedLong(LocalDateTime.now().getNano()));
                    String newFileName = Long.toString(fileNo++ + fileNo_sub) + "_notice_" + b.getOriginalFilename();
                    File dest_dir = cpr.getFile(); // TODO local
                    b.transferTo(new File(dest_dir, newFileName)); // TODO local
                    // b.transferTo(new File(localRoot + p, newFileName)); // TODO ec2

                    c.add(AttachedFile.builder()
                            .fileName(newFileName)
                            .filePath(p)
                            .fileContentType(b.getContentType())
                            .build());
                }
                // 지정해둔 로컬경로에 파일 업로드하고, 그 파일에 대한 정보들 담은 dto 만들어서 리턴
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    // 파일 받고싶어하면
    public static void downFile(HttpServletResponse response, AttachedFile afile)
            throws FileNotFoundException, IOException {
        String fileName = afile.getFileName();
        String url = localRoot + afile.getFilePath() + fileName;
        String contentType = afile.getFileContentType();
        File f = new File(url);
        long fileLength = f.length();
        // 일단 파일에 대한 정보들 지정해두고

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        // response에 header값 넣어서 내려보내줄건데

        FileInputStream fis = new FileInputStream(url);
        OutputStream os = response.getOutputStream();
        // 파일이 있는곳을 파라미터로 넣어 inputStream 객체를 만들고
        // response에서 파일을 내보낼 OutputStream을 가져와서
        int readCount = 0;
        byte[] buffer = new byte[1024];
        // 파일 읽을 만큼 크기의 buffer를 생성한 후
        while ((readCount = fis.read(buffer)) != -1) {
            os.write(buffer, 0, readCount);
            // outputStream에 쓴다. 보낸다.
        }
        fis.close();
        os.close();
    }

    // summernote사용해 파일 올라올 때
    public static String uploadFile_summernote(MultipartFile a) {
        // 제이슨오브젝트 빈 객체 하나 만들어서
        JsonObject jo = new JsonObject();
        ClassPathResource cpr = new ClassPathResource("static" + p); // TODO local
        String newFilename = Long.toString(fileNo++ + fileNo_sub) + "_summernote_" + a.getOriginalFilename();

        try {
            File fileRoot = cpr.getFile(); // TODO local
            File tDirectory = new File(fileRoot, newFilename); // TODO local
            a.transferTo(tDirectory); // TODO local
            // 파일 옮기고
            // a.transferTo(new File(localRoot + p, newFilename)); // TODO ec2
            jo.addProperty("url", p + newFilename);
            // 옮긴경로+파일명 값 을 url이라는 이름으로 제이슨오브젝트에 넣고
            jo.addProperty("responseCode", "success");
            // 이게 성공했다고 responseCode에 success 값도 넣어준다
        } catch (IOException e) {
            // 실패하면(?) responseCode에 failed 넣어줌
            jo.addProperty("responseCode", "failed");
        }

        // 이제 성공이건 실패건 responseCode가 들어있는 제이슨오브젝트를 스트링형태로 바꿔서 리턴
        return jo.toString();
    }

    // // 행사정보 등록 전용 - 포스터
    public static EventPoster upEventPoster(MultipartFile a) {
        if (a.getOriginalFilename().isEmpty()) {
            return null;
        } else {
            try {
                EventPoster c;
                String newFileName = Long.toString(fileNo++ + fileNo_sub) + "_poster_" +
                        a.getOriginalFilename();
                File dest_dir = cpr.getFile(); // TODO local
                a.transferTo(new File(dest_dir, newFileName)); // TODO local
                // a.transferTo(new File(localRoot + p, newFileName)); // TODO ec2
                c = EventPoster.builder()
                        .fileName(newFileName)
                        .filePath(p)
                        .build();
                return c;
                // 지정해둔 로컬경로에 파일 업로드하고, 그 파일에 대한 정보들 담은 dto 만들어서 리턴
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // // 행사정보 등록 전용 - 상세페이지
    public static List<EventDetailImg> upEventDetailImages(List<MultipartFile> a) {
        List<EventDetailImg> c = new ArrayList<>();
        for (MultipartFile b : a) {
            try {
                if (!b.getOriginalFilename().isEmpty()) {
                    String newFileName = Long.toString(fileNo++ + fileNo_sub) + "_event_detail_"
                            + b.getOriginalFilename();
                    File dest_dir = cpr.getFile(); // TODO local
                    b.transferTo(new File(dest_dir, newFileName)); // TODO local
                    // b.transferTo(new File(localRoot + p, newFileName)); // TODO ec2

                    c.add(EventDetailImg.builder()
                            .fileName(newFileName)
                            .filePath(p)
                            .build());
                }
                // 지정해둔 로컬경로에 파일 업로드하고, 그 파일에 대한 정보들 담은 dto 만들어서 리턴
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public static void deletePosterFile(EventPoster f) {
        File c = new File(localRoot + f.getFilePath() + f.getFileName());
        if (c.exists())
            // 그 정보로 파일 찾아본다음 있으면
            c.delete();
        // 삭제해버린다
    }

    public static void deleteDetailImage(EventDetailImg f) {
        File c = new File(localRoot + f.getFilePath() + f.getFileName());
        if (c.exists())
            // 그 정보로 파일 찾아본다음 있으면
            c.delete();
        // 삭제해버린다
    }

    public static void deleteFile(AttachedFile f) {
        File c = new File(localRoot + f.getFilePath() + f.getFileName());
        if (c.exists())
            // 그 정보로 파일 찾아본다음 있으면
            c.delete();
        // 삭제해버린다
    }
}
