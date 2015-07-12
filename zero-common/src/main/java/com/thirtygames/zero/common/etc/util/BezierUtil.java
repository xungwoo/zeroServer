package com.thirtygames.zero.common.etc.util;

public class BezierUtil {
	float getCurveValue(float x, String curve) {
		if (x==0) {
			return 0;
		} else if (x==1.0f) {
			return 1;
		}
		
		String pt[] = curve.split(",");
		if (pt.length!=4)
			return 0;
		
		float point[] = new float[4];
		int i=0;
		for (String item: pt) {
			point[i++] = Float.parseFloat(item);
		}
		
		float t = findTforX(0,point[0],point[2],1,x);
		return bezierat(0,point[1],point[3],1,t);
	}
	
	float bezierat(float a, float b, float c, float d, float t) {
		return (float) ((Math.pow(1 - t, 3) * a + 3 * t * (Math.pow(1 - t, 2))
				* b + 3 * Math.pow(t, 2) * (1 - t) * c + Math.pow(t, 3) * d));
	}
	
	float findTforX(float a, float b, float c, float d, float x) {
		float t = 0f;
		float left = a;
		float right = d;
		
		for (int i=0; i<15; i++) {
			t = left + (right - left)/2;
			float res = bezierat(a, b, c, d, t);

			if (res<x) {
				left = t;
			} else {
				right = t;
			}
		}
		
		return t;
	}	
}
