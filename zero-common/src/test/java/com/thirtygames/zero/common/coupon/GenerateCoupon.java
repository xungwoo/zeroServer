package com.thirtygames.zero.common.coupon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class GenerateCoupon {

	@Ignore
	@Test
	public void genCoupon() {
		String src = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random r = new Random();
		Map<String, String> couponMap = new HashMap<String, String>();
		String coupon = "";
		String prefix1 = "A6";

		int index = 0;
		while (couponMap.size() < 1000) {
			coupon = prefix1;
			//System.out.println("size::" + couponMap.size());
			while (coupon.length() < 8) {
				int spot = r.nextInt(36);
				coupon += src.charAt(spot);
			}

			//System.out.println("coupon::" + coupon);
			//System.out.println("couponMap.get(coupon)::" + couponMap.get(coupon));
			if (couponMap.get(coupon) == null) {
				couponMap.put(coupon, coupon);
				System.out.println(coupon);
			}

			index++;
		}
	}
	
	@Ignore
	@Test
	public void genCoupon2() {
		String src = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random r = new Random();
		Set<String> couponSet = new HashSet<String>();
		String coupon = "";
		
		int i=0;
		while (i < 100) {
			coupon = "";
			try {
				Thread.sleep(r.nextInt(100));
				long ts = System.currentTimeMillis();
				String tsStr = Long.toString(ts).substring(3, 10);
				for (int j=0; j<tsStr.length(); j++) {
					char number = tsStr.charAt(j);
					int n = Character.getNumericValue(number);  
					coupon += src.charAt(n);
				}
				System.out.println(coupon);				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			i++;
		}
		
	}
	
	
	
	/*
	@Test
	public void watermark() {
		String coupons[] = {
				"30G00HBU","30G06XC3","30G07137","30G0785T","30G0912C","30G0A1PQ","30G0AKNJ","30G0AQ9Y","30G0B5F1","30G0BSFH","30G0DAKU","30G0E8BA","30G0F0UN","30G0FKOD","30G0FN4Q","30G0FXIU","30G0GZXT","30G0H6RK","30G0I1GJ","30G0JEP7","30G0KC7U","30G0KTR9","30G0LNHD","30G0N46D","30G0N98F","30G0Q4XP","30G0QIZ0","30G0QJFF","30G0S8AJ","30G0SHSA","30G0SRM8","30G0UCZ9","30G0VJVC","30G0VMDO","30G0VO2W","30G0WJ5H","30G0XGC8","30G0XO3D","30G0Z9AC","30G0ZZKO","30G111YN","30G11YOA","30G120EU","30G127R8","30G12AQ1","30G133BO","30G13ULH","30G145NF","30G14CTJ","30G15BFC","30G17W7C","30G19S62","30G19UVS","30G1BE37","30G1DBV8","30G1E1HV","30G1EHQQ","30G1G801","30G1LEOE","30G1N6FJ","30G1NF2P","30G1OD2Q","30G1PE8A","30G1PIBE","30G1RU3Z","30G1S0FO","30G1SDIH","30G1TID2","30G1VAYD","30G1XI4X","30G23857","30G24FZI","30G24RFA","30G26N22","30G27J1L","30G292W2","30G2A8TN","30G2AJYX","30G2DDK6","30G2E2IG","30G2EOXB","30G2ES9T","30G2GAFQ","30G2HBRS","30G2JIJC","30G2OTY5","30G2S898","30G2TJYT","30G2V56C","30G2WZCK","30G2XD9K","30G2Y0KF","30G2Y76J","30G2YJFB","30G31MAQ","30G32EOL","30G35V05","30G37V9Q","30G386EW","30G3BN7H","30G3DIMC","30G3EJ5M","30G3EPH9","30G3F5UP","30G3G6PN","30G3ICNQ","30G3MA69","30G3NTUH","30G3O7GW","30G3OFDW","30G3PAG8","30G3QVGF","30G3RFM2","30G3SDOD","30G3TDPT","30G3U6IY","30G3VELG","30G3VYH6","30G3YFXX","30G42PIJ","30G4407G","30G4902T","30G495ZI","30G4CRUK","30G4EIMD","30G4EKKN","30G4FXE3","30G4H49K","30G4HHTI","30G4IPRV","30G4IQ0Y","30G4IYF4","30G4JHHR","30G4KGT4","30G4LHMJ","30G4NLYR","30G4OGW8","30G4OI7E","30G4P51O","30G4PE98","30G4PFCX","30G4PL5C","30G4QOKZ","30G4SKDH","30G4T8CU","30G4TR2H","30G4VAZX","30G4WQPC","30G4X9MB","30G5026I","30G516PJ","30G537SB","30G56JV3","30G58YT1","30G59DW6","30G5A46Z","30G5AR0Y","30G5BO79","30G5CSN2","30G5ECAK","30G5FBAR","30G5GLH4","30G5HVRH","30G5IXZG","30G5IZVI","30G5KCNJ","30G5KE3D","30G5NA1R","30G5PQ5U","30G5PX84","30G5QFO2","30G5QICJ","30G5S04G","30G5UOMG","30G5WT98","30G5ZFNN","30G632BT","30G63QKI","30G652G1","30G65867","30G65WC5","30G6734H","30G67KUI","30G6IG74","30G6JNWO","30G6JOQ4","30G6JU9A","30G6JW5A","30G6KTFO","30G6L71K","30G6MW2S","30G6OR5Z","30G6RXI8","30G6SXKU","30G6TZ0F","30G6UCBO","30G6UW2L","30G6WPBD","30G6WUUT","30G6X5TN","30G6X7A1","30G6XCJO","30G6XR2X","30G6ZDFL","30G700O0","30G71CLL","30G73KPV","30G74C87","30G760LD","30G7713E","30G77AQ5","30G791UI","30G79QXG","30G7A0I7","30G7CW4W","30G7E2IJ","30G7G8ZD","30G7GUBJ","30G7HNNQ","30G7J37T","30G7KRKD","30G7LL0O","30G7M8KW","30G7P9J0","30G7QYIP","30G7RKCZ","30G7S26M","30G7S8CB","30G7SKLJ","30G7SPQO","30G7SWSD","30G7UYMV","30G7W7IX","30G7X7PF","30G7X9HZ","30G7YAEO","30G7YFAP","30G7YM6F","30G7ZFA7","30G809HF","30G823LV","30G83BLO","30G83Z3B","30G84YN2","30G89NIG","30G8B8UV","30G8EDII","30G8FMXN","30G8HNXT","30G8I7J5","30G8JGLE","30G8L95M","30G8MDTU","30G8MHW9","30G8NB5E","30G8PWBW","30G8QVMY","30G8QXAW","30G8TP2B","30G8UAGK","30G8XY8O","30G8Y58B","30G8Y5VB","30G8YJEB","30G90U7W","30G91Z15","30G926MF","30G92MBR","30G934KE","30G940D0","30G949UM","30G94NX6","30G950FL","30G956IE","30G9AF2L","30G9B236","30G9F4RU","30G9F7GW","30G9FVO8","30G9I262","30G9JNEX","30G9L5K4","30G9MBOE","30G9QX6S","30G9RI1D","30G9SCLL","30G9SVDH","30G9T5SY","30G9T5UB","30G9WURR","30GA1F0E","30GA2CDI","30GA2M7J","30GA35N1","30GA39BP","30GA5GV9","30GA9LYM","30GA9VMC","30GA9X6C","30GAB326","30GADZZS","30GAFQUI","30GAG5Y6","30GAI273","30GAIUQB","30GAKPFZ","30GAL9OB","30GAMELV","30GANF2C","30GAOP9N","30GAPE2L","30GAQ1M8","30GAR7UF","30GARE3C","30GARWXM","30GAY8IC","30GAY9WL","30GAZEL8","30GAZN4Q","30GB18NF","30GB1Q08","30GB5A6P","30GB5N7V","30GB5NPJ","30GB933G","30GB9OYM","30GBACNG","30GBBO2E","30GBFKPP","30GBHHUK","30GBJHBB","30GBK0ED","30GBK3RB","30GBKVPB","30GBL7QR","30GBLZRO","30GBM796","30GBPYQF","30GBRP0C","30GBSG4G","30GBTBO1","30GBU0E2","30GBUJ7Y","30GBULBP","30GBV0YJ","30GBVAVI","30GBVYBD","30GBW87B","30GBWPU5","30GBWSLN","30GBX8ZQ","30GBZ598","30GBZ830","30GC0D5D","30GC15MW","30GC1K14","30GC1YCL","30GC1YQY","30GC2826","30GC53IC","30GC5CDW","30GC5IK8","30GC6R9L","30GC7VAO","30GC8SHU","30GC8WV0","30GCA8EQ","30GCABYK","30GCCU8R","30GCDDTP","30GCEC4G","30GCGR00","30GCHQFR","30GCHQG8","30GCKX50","30GCKYJH","30GCNALZ","30GCOL0S","30GCOW1G","30GCP30W","30GCQW51","30GCSAQ9","30GCUIAP","30GCWSTY","30GCZ79F","30GD1IOR","30GD532G","30GD59ZR","30GD5Z54","30GD6QFZ","30GD6XP3","30GD7K3B","30GD9WGS","30GDCA6O","30GDCXMI","30GDE44I","30GDMB5I","30GDNDQA","30GDNL2S","30GDOGEP","30GDPGLG","30GDQCXT","30GDTVL4","30GDW1LA","30GE1SYP","30GE5OEV","30GE6G8F","30GE7PJ7","30GE88RY","30GE8C4D","30GEEZ37","30GEFCJ5","30GEG6Y4","30GEH8Y1",
				"30GEIALW","30GEJUKX","30GEKE1Q","30GEM9P8","30GEN5A5","30GEN936","30GENU0N","30GEOQLG","30GEP8C5","30GEQ96V","30GESJVP","30GET9E8","30GETKD8","30GEUMF1","30GEUQRV","30GEVKWD","30GEVRN3","30GEX3M1","30GEXPXF","30GEYU2B","30GF00VV","30GF2QF6","30GF3178","30GF3VAH","30GF73AR","30GF84PS","30GFBRO8","30GFD5ZG","30GFHIT4","30GFIN70","30GFK0GA","30GFMHQD","30GFN40N","30GFNIU7","30GFOED2","30GFQHPX","30GFSEBX","30GFXUDI","30GFY3XX","30GG0NHZ","30GG3UEL","30GG56YM","30GG620M","30GG6V9T","30GG84TA","30GGAAC4","30GGBK6F","30GGBREU","30GGCVD0","30GGD767","30GGE3OW","30GGEJZS","30GGG7U1","30GGIHA3","30GGJ5JC","30GGJQRO","30GGJWGO","30GGKPPZ","30GGLR2O","30GGN06I","30GGOO53","30GGQX94","30GGUE4T","30GGWEMC","30GGXAUU","30GGYHFA","30GH18H6","30GH38B8","30GH4SQ3","30GH72LJ","30GH940M","30GHBNZF","30GHBSAA","30GHBTBA","30GHCA39","30GHD3Z3","30GHDWQO","30GHE93T","30GHEPB3","30GHEU01","30GHF595","30GHFA5J","30GHH00G","30GHH7EW","30GHKDJJ","30GHQW8Q","30GHRD58","30GHV7UD","30GHZAFQ","30GHZIHE","30GHZNZT","30GI0A4K","30GI36U7","30GI384R","30GI3JMN","30GI779R","30GI8XXL","30GIAP4C","30GIDX8C","30GIEVBV","30GIF10Z","30GIHQW4","30GILOZ5","30GIMJL1","30GINZ45","30GIRENM","30GIS5OX","30GISCZY","30GITZY2","30GIW45M","30GIX29A","30GIX2A9","30GIXB9V","30GIZ38J","30GIZT23","30GJ0U60","30GJ280H","30GJ5C1S","30GJ5HF1","30GJ5X6J","30GJ7DYB","30GJ87D8","30GJ8PQM","30GJ8YSL","30GJAKPT","30GJAYPB","30GJBQFV","30GJCQGT","30GJDHSI","30GJHXZY","30GJIJ50","30GJJDED","30GJQ48Y","30GJUM3T","30GJVKHR","30GJWWFH","30GJYOWU","30GJZZIQ","30GK0FYL","30GK0L96","30GK1ZIC","30GK2ZV8","30GK4RCD","30GK5R9C","30GK66VP","30GK8MB6","30GK8Q2A","30GK9J5D","30GK9WT3","30GKAV3Z","30GKCNWP","30GKCOVM","30GKCUJL","30GKDXAP","30GKFQOG","30GKGM2T","30GKHI2D","30GKHQFV","30GKIE01","30GKIGJZ","30GKJ8B6","30GKLMRY","30GKM4Q2","30GKQHWW","30GKTHMQ","30GKTNF0","30GKUGXC","30GKUXJB","30GKWLCM","30GL06LC","30GL1771","30GL21YK","30GL3LVF","30GL49NB","30GL4S0P","30GL52QT","30GL7QR3","30GL8KX1","30GL96YV","30GLBGQ7","30GLBPVW","30GLCT8R","30GLFOJG","30GLGCTM","30GLHY6U","30GLHYN2","30GLJFOP","30GLLLJ8","30GLM216","30GLQ0K8","30GLQZTT","30GLR9D1","30GLRC95","30GLU4DO","30GLUQMK","30GLXOI1","30GM11WE","30GM173H","30GM3XYW","30GM4VWJ","30GM5Y43","30GM7CC9","30GM83LW","30GM8ORI","30GM9164","30GM9MR0","30GMB52W","30GMC724","30GMDPOE","30GMF6ZD","30GMFZZ1","30GMG6AT","30GMGFMW","30GMHNUJ","30GMLR5Y","30GMM682","30GMOVZV","30GMP8SR","30GMRV85","30GMT21T","30GMUTPL","30GMV2CS","30GMVCW1","30GMWJ1Z","30GMXK9J","30GMY36X","30GN00Z2","30GN1CQ0","30GN44IW","30GN4C5T","30GN5EPF","30GN766T","30GN7PFI","30GN83O4","30GN8NU2","30GNCJD9","30GNGHI4","30GNJNDQ","30GNNIHQ","30GNOR6Q","30GNPEHI","30GNPT1O","30GNR77G","30GNTTOD","30GNTWPG","30GNU3T7","30GNUKSA","30GNUN7K","30GNYKLX","30GNYP01","30GO026D","30GO06IZ","30GO170H","30GO2AUF","30GO3ARS","30GO3EFU","30GO4ZRF","30GO75FA","30GO7H9B","30GO83U2","30GO92CJ","30GOA0H7","30GOAAWG","30GOAPF1","30GOCRDB","30GOCU4L","30GOD6D0","30GODWMN","30GOEHYE","30GOGY60","30GOH4AP","30GOKDGJ","30GOLD99","30GOPQSI","30GOQF6Z","30GOQUU4","30GOW3F9","30GOY8QV","30GOYL3X","30GP0NNF","30GP529R","30GP57R3","30GP61GO","30GP63HV","30GP6GXY","30GP6RV5","30GPBGWM","30GPC159","30GPFDII","30GPFUT4","30GPGK1I","30GPHODN","30GPHXE9","30GPI38O","30GPIN2G","30GPJ9NF","30GPMXP7","30GPO3U8","30GPOQZC","30GPS3MB","30GPSB11","30GPVTLU","30GPXG4V","30GPZ4DP","30GQ0VL7","30GQ136A","30GQ21LA","30GQ30OU","30GQ3DTN","30GQ5LI4","30GQ5X6L","30GQ6FYH","30GQ7CPX","30GQ7CZD","30GQ7IW3","30GQB0BP","30GQCLIZ","30GQCTB7","30GQF040","30GQF5ZD","30GQFK8U","30GQG2W1","30GQGXQH","30GQK2QN","30GQN5TF","30GQPMEQ","30GQT5OD","30GQTS5X","30GQU4D6","30GQY2O6","30GQZFZP","30GR0161","30GR0L7X","30GR2UCS","30GR69V9","30GR6KNL","30GR7LP3","30GR7QEA","30GR8ZZW","30GR98FB","30GR9JJ4","30GRAWN3","30GRBGFE","30GREVS4","30GRG87B","30GRGPRX","30GRGSAQ","30GRIDVF","30GRJXMN","30GRL6JE","30GRLDH9","30GRM2FN","30GRMCGQ","30GRMPLE","30GRNL0X","30GRPH17","30GRQ0N1","30GRQJQ0","30GRQR76","30GRRE0F","30GRRLIO","30GRS9BV","30GRUEGG","30GRW4QM","30GRYXIP","30GRZP9D","30GS1X59","30GS2IOP","30GS4V08","30GS74EM","30GS8905","30GS8K7P","30GS8MUI","30GSAUI1","30GSBBGY","30GSCJYY","30GSCKUM","30GSEC2O","30GSETBE","30GSH4KR","30GSI499","30GSIMFM","30GSLVYP","30GSMW2Q","30GSPEM0","30GSVXOV","30GSW1QA","30GSW7JZ","30GSWPCM","30GSX57Y","30GSYFY6","30GSZF11","30GSZZMO","30GT1SOI","30GT2QSY","30GT4OR9","30GT5QFV","30GT6LTY","30GT7P7B","30GT8HUU","30GT8K9U","30GT90SB","30GT9JM4","30GT9YL3","30GTFTSQ","30GTI4Q6","30GTIAHG","30GTL55G","30GTODYT","30GTOHEV","30GTOY0Q","30GTQBPE","30GTQS5U","30GTRJ4C",
				"30GTRWB4","30GTTUIR","30GTW61C","30GTWL21","30GTWYM4","30GTX7HZ","30GTXJE5","30GTYMKR","30GU4URW","30GU9UJ0","30GUDX67","30GUIOAU","30GUJR3Z","30GULVVV","30GUN90E","30GUNYKP","30GUO3G5","30GUOSSR","30GUOUUT","30GUQJPH","30GURK1L","30GURS4W","30GUSG4G","30GUX5G5","30GUX61T","30GV05VR","30GV0ZNQ","30GV1F0L","30GV1J0O","30GV2J8J","30GV4BAZ","30GV6PKG","30GV7RK3","30GV7S3R","30GV7WCA","30GV89EQ","30GV9HJ7","30GVAADX","30GVAVY8","30GVCXF5","30GVDK2S","30GVEOHP","30GVGJIJ","30GVIDY9","30GVIHA8","30GVJ2B9","30GVJJCI","30GVK18O","30GVKRH2","30GVN3MA","30GVTTNM","30GVU2W6","30GVUYKS","30GW12B3","30GW15O1","30GW3OKQ","30GW61DW","30GW62VT","30GW7X0K","30GWA7RT","30GWBW8X","30GWDFKY","30GWEX1O","30GWEZC7","30GWFPG6","30GWFZIC","30GWI73E","30GWILIN","30GWIPHS","30GWJTQ8","30GWMR5C","30GWOFNP","30GWOJO4","30GWP7O5","30GWPBZO","30GWPU0O","30GWQVXR","30GWQZTP","30GWRA73","30GWRG6N","30GWT30D","30GWT5DH","30GWTAUF","30GWTZ8U","30GWU2IW","30GWURPH","30GWXQ0O","30GX356B","30GX3JFL","30GX44J5","30GX4L7G","30GX6T2K","30GX8EEN","30GX8P68","30GX9J1T","30GXBGFC","30GXBIAW","30GXBOVO","30GXE5G3","30GXEXTB","30GXGTPP","30GXGYJ2","30GXIEHV","30GXKZ5L","30GXL7AZ","30GXM1CW","30GXMNQQ","30GXPZPH","30GXQP0B","30GXT4QQ","30GXT8IB","30GXTZLU","30GXXMJV","30GXYT7D","30GXZT67","30GY2NI9","30GY4L7R","30GY8ELF","30GY8EUZ","30GYA1Z0","30GYCQR5","30GYCZN4","30GYDHZP","30GYE7ZN","30GYEB2L","30GYGD5F","30GYI776","30GYJ9QA","30GYKJ51","30GYKUJ3","30GYMJH0","30GYN2PY","30GYOUHO","30GYOXDM","30GYQ4LO","30GYRFOV","30GYSDXP","30GYTLWA","30GYUNFP","30GYVHR7","30GYWG6E","30GYYDI8","30GZ0FOE","30GZ11X1","30GZ2OHS","30GZ3A0P","30GZ3NCU","30GZ4V9E","30GZ8NKC","30GZ9JQF","30GZAHIZ","30GZBSXD","30GZCB91","30GZE3UC","30GZEQF6","30GZFD87","30GZFLKY","30GZGOFV","30GZGYG6","30GZHAUY","30GZHX6N","30GZI8YY","30GZIKG7","30GZJHRL","30GZJMNS","30GZL2ZP","30GZM5WC","30GZNX3I","30GZP3SE","30GZRN6Q","30GZUHBZ","30GZXV67"
				};		
		
        //100건에 4초가량 소요됨
//        System.out.println("======>"+DateTimeUtil.getCurTime());
        for (int i = 0; i <coupons.length; i++) {
        	log.debug("coupons[i]::" + coupons[i]);
        	
        	File sourceImageFile = new File("D:cccoupon.jpg");
        	//File destImageFile = new File("e:/coupon/" + coupons[i] + "_w.jpg");
        	File destImageFile = new File("D:/coupon/" + coupons[i] + "_w.jpg");
        	 
        	addTextWatermark(coupons[i], sourceImageFile, destImageFile);
        	
        	//BufferedImage waterMarkedImage = this.makeTextToImage(coupons[i]);
            //this.generateWaterMaker("D:/cccoupon.jpg", "D:/coupon/" + coupons[i] + "_w.jpg", waterMarkedImage, Integer.parseInt("100"));
        	System.out.println("======>i::" + i);
        }
	}
	
	public BufferedImage makeTextToImage(String text) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.GRAY);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        
        String imageName = text + ".png";
        try {
            ImageIO.write(img, "png", new File(imageName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }		
        
        return img;
	}
	
	static void addTextWatermark(String text, File sourceImageFile, File destImageFile) {
	    try {
	        BufferedImage sourceImage = ImageIO.read(sourceImageFile);
	        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
	 
	        // initializes necessary graphic properties
	        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
	        g2d.setComposite(alphaChannel);
	        g2d.setColor(Color.BLACK);
	        g2d.setFont(new Font("Arial", Font.BOLD, 64));
	        FontMetrics fontMetrics = g2d.getFontMetrics();
	        Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);
	 
	        // calculates the coordinate where the String is painted
	        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
	        int centerY = sourceImage.getHeight() / 2;
	 
	        // paints the textual watermark
	        g2d.drawImage(sourceImage, 0 ,0, null);
	        g2d.drawString(text, centerX - 162, centerY + 170);
	        g2d.drawImage(sourceImage, 0 ,0, null);
	 
	        ImageIO.write(sourceImage, "png", destImageFile);
	        g2d.dispose();
	 
	        System.out.println("The tex watermark is added to the image.");
	 
	    } catch (IOException ex) {
	        System.err.println(ex);
	    }
	}	
	
	public void generateWaterMaker(String originImg, String outputImg, BufferedImage watermarkImg, int maxDim) {

        BufferedImage[] m_images = new BufferedImage[4];
        try {
            m_images[0] = ImageIO.read(new File(originImg));
            m_images[1] = watermarkImg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //값 조정
        Image transpImg = ImageUtil.transformGrayToTransparency(m_images[1]);
        
//      ChangeScale
        //m_images[2] = ImageUtil.adjustScale(m_images[0], maxDim, true);
        m_images[2] = m_images[0];

//      Watermark 
        m_images[3] = ImageUtil.applyTransparencyThumbnail(m_images[2], transpImg);
        
        BufferedImage org = m_images[0];

        // 대상 이미지 버퍼 생성. - maxDim으로 변경된 사이즈
        BufferedImage outImage = new BufferedImage(org.getWidth(), org.getHeight(), BufferedImage.TYPE_INT_RGB);

        try {
            ImageWriteParam iwparam  = new JPEGImageWriteParam(Locale.getDefault());
            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwparam.setCompressionQuality(1f);
            
            // create new File for the new Image
            //실제 저장되어지는 파일명
            FileOutputStream  fos = new FileOutputStream(outputImg);

            Graphics2D g2s = outImage.createGraphics();
            g2s.drawImage(m_images[3],0,0, null);
            //watermark가 좀더 흐려짐
            //g2s.drawImage(m_images[3],0,0, null);
            
            g2s.setColor(new Color(239, 239, 239));
            g2s.drawRoundRect(2, 2, 95, 95, 0, 0);
            g2s.setColor(new Color(180, 180, 180));
            g2s.drawRoundRect(1, 1, 97, 97, 0, 0);
            g2s.setColor(new Color(50, 50, 50));
            g2s.drawRoundRect(0, 0, 99, 99, 0, 0);
            g2s.dispose();

//            ImageWriter writer = getWriter(".jpg");  // 색이 좀더 진하고 용량이 좀더 늘어남.
            ImageWriter writer = ImageUtil.getWriter(".png");  // 용량도 적고 색도 자연스럽다.>>> 추천
            ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(outImage, null, null), iwparam);
            ios.close();
            fos.close();
            writer.dispose();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } 			
*/	

}
