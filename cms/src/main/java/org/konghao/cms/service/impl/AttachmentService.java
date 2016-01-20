package org.konghao.cms.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.model.Attachment;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.IAttachmentService;
import org.springframework.stereotype.Service;
@Service("attachmentservice")
public class AttachmentService extends BaseService implements
		IAttachmentService {
	public final static String UPLOAD_PATH="/resources/upload/";
	public final static String THUMNAIL_UPLOAD_PATH="/resources/upload/thumbnail/";
	public final static int IMG_WIDTH = 900;
	public final static int THUMBNAIL_WIDTH = 50;
	public final static int THUMBNAIL_HEIGHT = 50;
	@Override
	public void add(Attachment a,InputStream ins) {
		attachmentDao.add(a);
		addFile(a,ins);
	}
     
	
	/**function(储存文件到服务器)
	 * @param  @param a     附件的对象
	 * @param  @param ins   附件的输入流       
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月27日
	*/
	private void addFile(Attachment a, InputStream ins) {
		//得到文件储存的路径
		String  realpath=SystemContext.getRealPath();
		String path = realpath+"/resources/upload/";
		//图片压缩后储存的路径
		String thumbPath = realpath+"/resources/upload/thumbnail/";
		File  file=new File(realpath);
		File  thumbnail=new File(thumbPath);
		//如果文件不存在的话，自动创建文件夹
		if(!file.exists()){
			file.mkdirs();
		}
		if(!thumbnail.exists()){
			thumbnail.mkdirs();
		}
		//文件储存的真实的目录
		path = path+a.getNewName();
		thumbPath = thumbPath+a.getNewName();
		//如果是图片的话就进行切割
		try {
		if(a.getIsImg()==1){
			BufferedImage read = ImageIO.read(ins);
			int width = read.getWidth();
			Builder<BufferedImage> bf = Thumbnails.of(read);
			if(width>IMG_WIDTH){
				bf.scale((double)IMG_WIDTH/(double)width);
			}else {
				bf.scale(1.0f);
			}
			bf.toFile(path);
			//缩略图的处理
			//1、将原图进行压缩
			BufferedImage tbi = Thumbnails.of(read).
					scale((THUMBNAIL_WIDTH*1.2)/width).asBufferedImage();
			//2、进行切割并且保持
			Thumbnails.of(tbi).scale(1.0f)
				.sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT)
				.toFile(thumbPath);
		}
		else{
				FileUtils.copyInputStreamToFile(ins, new File(path));
		  } 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**function(删除硬盘上的文件)
	 * @param  @param a         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public static void deleteAttachFiles(Attachment a) {
		String realPath = SystemContext.getRealPath();
		realPath +=UPLOAD_PATH;
		System.out.println("----------------"+realPath+a.getNewName());
		new File(realPath+a.getNewName()).delete();
		 realPath = SystemContext.getRealPath();
		new File(realPath+THUMNAIL_UPLOAD_PATH+a.getNewName()).delete();
		
	}
	
	@Override
	public void delete(int id) {
		Attachment a = attachmentDao.load(id);
		attachmentDao.delete(id);
		deleteAttachFiles(a);

	}

	@Override
	public Attachment load(int id) {
		return attachmentDao.load(id);
	}

	@Override
	public Pager<Attachment> findNoUseAttachment() {
		
		return attachmentDao.findNoUseAttachment();
	}

	@Override
	public long findNoUseAttachmentNum() {
		
		return attachmentDao.findNoUseAttachmentNum();
	}

	@Override
	public void clearNoUseAttachment() {
		attachmentDao.clearNoUseAttachment();

	}

	@Override
	public void deleteByTopic(int tid) {
		
        attachmentDao.deleteByTopic(tid);
	}

	@Override
	public List<Attachment> listByTopic(int tid) {
		
		return attachmentDao.listByTopic(tid);
	}

	@Override
	public List<Attachment> listIndexPic(int num) {
		
		return attachmentDao.listIndexPic(num);
	}

	@Override
	public Pager<Attachment> findChannelPic(int cid) {
		
		return attachmentDao.findChannelPic(cid);
	}

	@Override
	public Pager<Attachment> listAllIndexPic() {
		
		return attachmentDao.listAllIndexPic();
	}

	@Override
	public List<Attachment> listAttachByTopic(int tid) {
		
		return attachmentDao.listAttachByTopic(tid);
	}


	@Override
	public void updateIndexPic(int id) {
		Attachment att = attachmentDao.load(id);
		if(att.getIsIndexPic()==0){
			att.setIsIndexPic(1);
		}
		else{
			att.setIsIndexPic(0);
		}
		attachmentDao.update(att);
	}


	@Override
	public void updateAttachInfo(int id) {
		Attachment att = attachmentDao.load(id);
		if(att.getIsAttach()==0){
			att.setIsAttach(1);
		}
		else{
			att.setIsAttach(0);
		}
		attachmentDao.update(att);
	}

}
