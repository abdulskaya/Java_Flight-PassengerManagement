package newpackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Ucak{
	
	private String ucusNoktasi;
	
	String koltuklar[][][] = new String[10][2][4];
	/*
	10 satır, 2 sütun olarak bir uçağın koltuk yapısıyla benzer bir dizi oluşturduk.
	Dizinin 3. boyutunun(4) 0. indisinde dolu olup olmadığı, diğer indislerde diziye kaydedilecek kişinin ad, soyad, tc bilgilerini tutacağız.
	*/
	
	public Ucak() {
		this.koltuklarıDuzenle();
	}
	
	public Ucak(String ucusNoktasi) {
		this.ucusNoktasi = ucusNoktasi;
	}
	
	public void setUcusNoktasi(String ucusNoktasi) {
		this.ucusNoktasi = ucusNoktasi;
	}
	
	public String getUcusNoktasi() {
		return this.ucusNoktasi;
	}
	
	public void koltuklarıDuzenle() {
		//koltuklara boş değerlerini atadık
		for (int i = 0; i< 10; i++) {
			for (int j = 0; j < 2; j++) {
				for (int j2 = 0; j2 < 4; j2++) {
					this.koltuklar[i][j][j2] = "BOŞ";
				}
				
			}
		}
	}
	
	
	public void koltuklariGoster() {
		System.out.println(this.getUcusNoktasi()+" Koltuk Durumu:");
		System.out.print("\t  A       B\n");
		for (int i = 0; i < koltuklar.length; i++) {
			for (int j = 0; j < koltuklar[0].length; j++) {
				if(j==0) System.out.print(i+1);
				System.out.print("\t"+this.koltuklar[i][j][0]+"  ");
			}
			System.out.println();
		}
	}
	
	public void yolcuKaydi(UcusListesi Liste) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Koltuk Seçim Ekranı:\n(Lütfen koltuk numaranızın önce satırını sonra sütununu girin)\nSatir girin (Orn: 5):");
		int satir = sc.nextInt()-1; 
		System.out.println("Sütun girin(Orn B):");
		String hataDuzeltme = sc.nextLine();//Java okuma hatasını düzeltiyoruz.
		String sutunGecici = sc.nextLine();
		int sutun = 0;
		if(sutunGecici.equals("B") || sutunGecici.equals("b")) {
			sutun = 1; 
			sutunGecici = "B";
		}
		else if (sutunGecici.equals("A") || sutunGecici.equals("a")) {
			sutun = 0;
			sutunGecici = "A";
		}
		System.out.println("Yolcu ismi girin: ");
		this.koltuklar[satir][sutun][1] = sc.nextLine();
		System.out.println("Yolcu soyismi girin: ");
		this.koltuklar[satir][sutun][2] = sc.nextLine();
		System.out.println("Yolcu TC girin: ");
		this.koltuklar[satir][sutun][3] = sc.nextLine();
		this.koltuklar[satir][sutun][0] = "DOL";
		this.koltuklariGoster();
		Liste.UcusEkle(this.ucusNoktasi, Integer.toString((satir+1)), sutunGecici,this.koltuklar[satir][sutun][1] , this.koltuklar[satir][sutun][2], this.koltuklar[satir][sutun][3]);
		System.out.println("Yolcu kaydedildi!");
	}
	
	public void BiletIptal(UcusListesi Liste) { 
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Yolcu TC girin: ");
		String TC = sc.nextLine();
		
		for (int i = 0; i < koltuklar.length; i++) {
			for (int j = 0; j < koltuklar[0].length; j++) {
				String temp = koltuklar[i][j][3]; 
				if(temp.equals(TC)) {
					System.out.println("\t"+this.getUcusNoktasi()+" seferi için "+koltuklar[i][j][1]+" "+koltuklar[i][j][2]+" adlı uçuş iptal edildi.");
					this.koltuklar[i][j][0] = "BOŞ";
					this.koltuklar[i][j][1] =  null;
					this.koltuklar[i][j][2] =  null;
					this.koltuklar[i][j][3] =  null;
					Liste.UcusCikart(this.ucusNoktasi, TC);
				}
			}
		}
	}
	
	public int bosKoltukSayisi() {
		
		int sayac = 0;
		String z = "BOŞ";
		String x = null; // dizideki eleman null veya "BOŞ" değerini aldığında da boş olarak gözükmesini istiyoruz
		for (int i = 0; i < koltuklar.length; i++) {
			for (int j = 0; j < koltuklar[0].length; j++) {
				String y = koltuklar[i][j][0];
				if(z.equals(y) || x == y ) sayac++;
			} 
		}
		return sayac;
	}
	
	public int doluKoltukSayisi() {

		int sayac = 0;
		String x = "DOL";

		for (int i = 0; i < koltuklar.length; i++) {
			for (int j = 0; j < koltuklar[0].length; j++) {
				String y = koltuklar[i][j][0];
				if(x.equals(y)) sayac++;
			} 
		}
		return sayac;
	}
	
}
 
class UcusListesi{
	
	String UcusListesi[][] = new String[100][5];//Aynı anda en fazla 100 uçuş gerçekleşebilir.
	
	 File file = new File("UcusListesi.txt");
	 
	public void ListeyiDosyayaYazdir(Ucak u1, Ucak u2, Ucak u3, Ucak u4, Ucak u5) {
		
	     try {
	    	 FileWriter fileW = new FileWriter(file, false);
		     BufferedWriter bW = new BufferedWriter(fileW);
		     for (int i = 0; i < UcusListesi.length; i++) {
		    	 if(UcusListesi[i][4]!=null)
		    	 bW.write((i+1)+". "+this.UcusListesi[i][0]+" KoltukNo: "+this.UcusListesi[i][1]+" Ad Soyad: "+this.UcusListesi[i][2]+" "+this.UcusListesi[i][3]+" TC: "+this.UcusListesi[i][4]+"\n");
			}
		     bW.write("\t"+u1.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u1.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u1.doluKoltukSayisi());
		     bW.write("\t"+u2.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u2.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u2.doluKoltukSayisi());
		     bW.write("\t"+u3.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u3.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u3.doluKoltukSayisi());
		     bW.write("\t"+u4.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u4.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u4.doluKoltukSayisi());
		     bW.write("\t"+u5.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u5.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u5.doluKoltukSayisi());
		     bW.close();
		     System.out.println("Liste dosyaya yazıldı.");
	     }catch(IOException e) {
	    	 e.printStackTrace();
	     }
	}
	 
	public void ListeyiYazdir(Ucak u1, Ucak u2, Ucak u3, Ucak u4, Ucak u5) {
		for (int i = 0; i < UcusListesi.length; i++) {
				if(this.UcusListesi[i][4]!=null) {
					System.out.print("\t"+(i+1)+". "+this.UcusListesi[i][0]+" KoltukNo: "+this.UcusListesi[i][1]+" Ad Soyad: "+this.UcusListesi[i][2]+" "+this.UcusListesi[i][3]+" TC: "+this.UcusListesi[i][4]+"\n");
				}
				
		}
		System.out.println();
		System.out.println("\t"+u1.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u1.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u1.doluKoltukSayisi());
		System.out.println("\t"+u2.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u2.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u2.doluKoltukSayisi());
		System.out.println("\t"+u3.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u3.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u3.doluKoltukSayisi());
		System.out.println("\t"+u4.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u4.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u4.doluKoltukSayisi());
		System.out.println("\t"+u5.getUcusNoktasi()+" Seferi, boş koltuk sayısı: "+u5.bosKoltukSayisi()+" Dolu koltuk sayısı: "+u5.doluKoltukSayisi());
	}

	public void UcusEkle(String sefer, String koltukSatir, String koltukSutun, String ad, String soyad, String TC) {
		for (int i = 0; i < UcusListesi.length; i++) {
				if(this.UcusListesi[i][4]==null) {
					this.UcusListesi[i][0] = sefer;
					String koltukNo = koltukSatir+koltukSutun;
					this.UcusListesi[i][1] = koltukNo;
					this.UcusListesi[i][2] = ad;
					this.UcusListesi[i][3] = soyad;
					this.UcusListesi[i][4] = TC;
					break;
				}
		}
		
	}
	
	public void UcusCikart(String sefer, String TC) {
		for (int i = 0; i < UcusListesi.length; i++) {
			if(this.UcusListesi[i][0].equals(sefer) && this.UcusListesi[i][4].equals(TC) ) {
				this.UcusListesi[i][0] = null;
				this.UcusListesi[i][1] = null;
				this.UcusListesi[i][2] = null;
				this.UcusListesi[i][3] = null;
				this.UcusListesi[i][4] = null;
				break;
			}
		}
	}
		
	
}

public class Main {
	public static void main(String[] args) { 
		
		
		int tus, ucus;
		Scanner sc = new Scanner(System.in);
		

		Ucak ucak1 = new Ucak("Izmir-Adana");
		Ucak ucak2 = new Ucak("Izmir-Kayseri");
		Ucak ucak3 = new Ucak("Izmir-Antalya");
		Ucak ucak4 = new Ucak("Izmir-Erzurum");
		Ucak ucak5 = new Ucak("Izmir-Trabzon");
		
		UcusListesi Liste = new UcusListesi();
		
		do{
			System.out.println("[1].Yolcu Kaydı");
			System.out.println("[2].Bilet İptali");
			System.out.println("[3].Uçuş Listesi");
			System.out.println("[-1].Çıkış");
			System.out.println("Lütfen seçim yapınız:");
			tus = sc.nextInt();
			if(tus>3 || tus<-1 || tus ==0) {
				System.out.println("Geçersiz seçim!!");
				break;
			}
			switch(tus) {
				case 1:
					
					//Yolcu Kaydı
					System.out.println("Uçuşlar:");
					System.out.println("\t[1] -> "+ucak1.getUcusNoktasi());
					System.out.println("\t[2] -> "+ucak2.getUcusNoktasi());
					System.out.println("\t[3] -> "+ucak3.getUcusNoktasi());
					System.out.println("\t[4] -> "+ucak4.getUcusNoktasi());
					System.out.println("\t[5] -> "+ucak5.getUcusNoktasi());
					System.out.println("Uçuş seçiniz: ");
					ucus = sc.nextInt();
					switch(ucus) {
						case 1:
							ucak1.koltuklarıDuzenle();
							ucak1.koltuklariGoster();
							ucak1.yolcuKaydi(Liste);
							break;
						case 2:
							ucak2.koltuklarıDuzenle();
							ucak2.koltuklariGoster();
							ucak2.yolcuKaydi(Liste);
							break;
						case 3:
							ucak3.koltuklarıDuzenle();
							ucak3.koltuklariGoster();
							ucak3.yolcuKaydi(Liste);
							break;
						case 4:
							ucak4.koltuklarıDuzenle();
							ucak4.koltuklariGoster();
							ucak4.yolcuKaydi(Liste);
							break;
						case 5:
							ucak5.koltuklarıDuzenle();
							ucak5.koltuklariGoster();
							ucak5.yolcuKaydi(Liste);
							break;
						default:
							System.out.println("Hatalı giriş!");
							break;
					
					}
					break;
				case 2:
					//Bilet iptal
					System.out.println("Uçuşlar:");
					System.out.println("\t[1] -> "+ucak1.getUcusNoktasi());
					System.out.println("\t[2] -> "+ucak2.getUcusNoktasi());
					System.out.println("\t[3] -> "+ucak3.getUcusNoktasi());
					System.out.println("\t[4] -> "+ucak4.getUcusNoktasi());
					System.out.println("\t[5] -> "+ucak5.getUcusNoktasi());
					System.out.println("İptal etmek istediğiniz çuşunuz hangi sefere ait: ");
					ucus = sc.nextInt();
					switch(ucus) {
					case 1:
						ucak1.BiletIptal(Liste);
						break;
					case 2:
						ucak2.BiletIptal(Liste);
						break; 
					case 3:
						ucak3.BiletIptal(Liste);
						break;
					case 4:
						ucak4.BiletIptal(Liste);
						break;
					case 5:
						ucak5.BiletIptal(Liste);
						break;
					default:
						System.out.println("Hatalı giriş!");
						break;
					}
					break;
				case 3:
					//Uçuş Listesi
					System.out.println("Seçim Yapınız:");
					System.out.print("\t[1]Uçuş Listesini Göster\n");
					System.out.print("\t[2]Uçuş Listesini Dosyaya Yazdır");
					int listeSecim = sc.nextInt();
					
					switch(listeSecim) {
						case 1:
							Liste.ListeyiYazdir(ucak1, ucak2, ucak3, ucak4, ucak5);
							break;
						case 2:
							Liste.ListeyiDosyayaYazdir(ucak1, ucak2, ucak3, ucak4, ucak5);
							break;
						default:
							System.out.println("Hatalı giriş!");
							break;
					}
			}
				
		}while(tus!=-1);
		System.out.println("Sistemden çıkıldı!");
	}
}
