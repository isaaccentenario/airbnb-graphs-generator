import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.restfb.*;
import com.restfb.types.*;
import com.restfb.util.*;
import com.restfb.BinaryAttachment;

public class Facebook {
	
	private String token, appid, chavePrivada, status, nomeImg;
	File imagem;
	ArrayList<FaceTag> tags = new ArrayList<FaceTag>();

	public Facebook setToken(String token) {
		this.token = token;
		
		return this;
	}

	public Facebook setAppid(String appid) {
		this.appid = appid;
		return this;
	}

	public Facebook setChavePrivada(String chavePrivada) {
		this.chavePrivada = chavePrivada;
		
		return this;
	}
	
	public Facebook setImagem(String nome, File imagem) {
		this.nomeImg = nome;
		this.imagem = imagem;
		return this;
	}
	
	public Facebook setStatus(String status) {
		this.status = status;
		return this;
	}
	
	public Facebook addTag(String uid, String text) {
		this.tags.add(new FaceTag( uid,text ));
		
		return this;
	}
	
	public void post() throws FileNotFoundException
	{
		FacebookClient fb = new DefaultFacebookClient( this.token );
		//FileInputStream img;
		
//		try {
//			img = new FileInputStream( this.imagem );
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//			return;
//		}
		System.out.println(this.imagem.getAbsolutePath());
		FacebookType response = fb.publish("me/photos", FacebookType.class, 
				BinaryAttachment.with(this.status, new FileInputStream( this.imagem.getAbsolutePath() ) ),
				Parameter.with("message", this.status ),
				Parameter.with("tags", this.tags ));
	}
}
