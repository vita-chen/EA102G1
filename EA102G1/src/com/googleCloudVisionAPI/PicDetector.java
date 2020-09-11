package com.googleCloudVisionAPI;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

public class PicDetector {
	public boolean detectPic(Part part){
		boolean result = true;
		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

			// The path to the image file to annotate
			InputStream is = part.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int i = 0;
			byte[] buffer = new byte[8192];
			while ((i = is.read(buffer)) != -1) {
				bos.write(buffer, 0, i);
			}
			// Reads the image file into memory
			byte[] data = bos.toByteArray();
			bos.close();
			is.close();
			ByteString imgBytes = ByteString.copyFrom(data);

			// Builds the image annotation request
			List<AnnotateImageRequest> requests = new ArrayList<>();
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// Performs label detection on the image file
			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			List<String> warning = new ArrayList<String>();
			warning.add("Cat");
			warning.add("Mammal");
			warning.add("Felidae");

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					break;
				}
				List<String> results = new ArrayList<String>();
				for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
					annotation.getAllFields().forEach((k, v) -> results.add(v.toString()));
				}
				System.out.println(results);
				for (String str : warning) {
					if (results.contains(str)) {
						result = false;
					}
				}
			}
			vision.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} 
}
