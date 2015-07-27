import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.naming.directory.Attribute;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class file{
	public static void main(String args[]){
		
		String file_name = "";
		int count = 0;
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> para_number = new ArrayList<Integer>();
		ArrayList<String> para_name = new ArrayList<String>();
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("file_name:");//編集する.nlogo名
			file_name = scan.next();
			System.out.print("imput number of prams:");//パラメータの数
			count=scan.nextInt();
			for(int i=0;i<count;i++){
				System.out.print("imput pram_name:");//パラメータ名
				para_name.add(scan.next());
				System.out.print("imput pram value:");//パラメータの数値
				para_number.add(scan.nextInt());
			}
		//Fileクラスのインスタンス化
		File file = new File(file_name);
		if(file_test(file)){
			 //file_Edit(file,para_name,para_number);
			String editText = file_Edit(file,para_name,para_number);
			file_Write(file, editText);
		}
	}

	private static boolean file_test(File file_name){
		//設定したファイルがあるかどうかの判定
		if(!file_name.exists()){
			//ファイルが存在しない場合処理終了
			System.out.println("not found file.");
			return false;
		}
		// 指定されたパスがファイルかどうかを判定
        else if ( !file_name.isFile() ) {
            // ディレクトリを指定した場合は処理終了
            System.out.println( "this is not a excutable file.check if a file is excutable." );
            return false;
        }

        // ファイルが読み込み可能かどうかを判定
        else if ( !file_name.canRead() ) {
            // ファイルが読み込み不可の場合は処理終了
            System.out.println("can not read a file.");
            return false;
        }

        // ファイルが書き込み可能かどうかを判定
        else if ( !file_name.canWrite() ) {
            // ファイルが読み込み不可の場合は処理終了
            System.out.println("can not write a file.");
            return false;
        }
        else{ 
        	return true;
        }
	}
	private static String file_Edit(File file_name, ArrayList name, ArrayList number){
		StringBuffer fileRead = new StringBuffer("");
		StringBuffer xmlRead = new StringBuffer("");
		File file = new File("hoge.xml");
		int i=0;
		try{
			file.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(file_name));
			BufferedReader brtmp = new BufferedReader(new FileReader(file));
			String str = null;
			String tmp = null;
			while((tmp=br.readLine()) != null){//1行読み込み
				if(tmp.equals("<experiments>")){//<experiments>に当たったら別の処理
				 	xmlRead.append(tmp+"\r\n");//別枠で保存
				 	while(!(tmp=br.readLine()).equals("</experiments>")){//</experiments>までループ
				 		xmlRead.append(tmp+"\r\n");//xmlに追加</experiments>まで追加
				 	}
				 	xmlRead.append(tmp+"\r\n");//</experiments>を追加
				 	file_Write(file,xmlRead.toString());//hogeに書き込み
				 	xmlEdit(file, name, number);//hogeを編集
				 	while((str=brtmp.readLine()) != null){
				 		if(i==1){
				 			fileRead.append(str+"\r\n");
				 		}
				 		else{
				 			i++;
				 		}
				 	}

				}else{
					str = tmp;
					fileRead.append(str+"\r\n");
					//System.out.println(str);
				}
			}
			br.close();
			brtmp.close();
		}catch (FileNotFoundException ex){
			 System.out.println(ex);
		}catch(IOException ex){
			System.out.println(ex);
		}
		return fileRead.toString();
	}
	private static void file_Write(File file_name, String editText){
		try {

            // FileWriterクラスをインスタンス化
            FileWriter filewriter = new FileWriter(file_name);

            filewriter.write(editText);

            // ファイルを閉じる
            filewriter.close();

        } catch ( IOException ex ) {
             System.out.println("106:"+ex);
        }
	}
	private static void xmlEdit(File file, ArrayList name, ArrayList number){
		try{
			Document document= DocumentBuilderFactory
       		                 .newInstance()
   	    	                 .newDocumentBuilder()
       		                 .parse(new File("hoge.xml"));
       		NodeList nodeList = document.getElementsByTagName("enumeratedValueSet");//全てのenumeratedValueSetを洗い出し
       		Element element = document.getDocumentElement();
       		for(int y=0;y<name.size();y++){
       			for(int x=0;x<name.size();x++){
       				if((name.get(y).toString()).equals(nodeList.item(x).getAttributes().item(0).getNodeValue())){//density
       					 element.getElementsByTagName("value").item(x).getAttributes().item(0).setNodeValue(number.get(y).toString());
       				}
       			}
       		}
       		Transformer transformer = null;
   		 	try {
   		     	TransformerFactory factory = TransformerFactory.newInstance();
   		     	transformer = factory.newTransformer();
       		} catch (TransformerConfigurationException e) {
           		// 通常はありえない。
           		e.printStackTrace();
       		}
       		transformer.setOutputProperty("indent",   "yes");
       		transformer.setOutputProperty("encoding", "UTF-8");
       		transformer.setOutputProperty("omit-xml-declaration", "no");
       		try {
       		    transformer.transform(new DOMSource(document),
       		                          new StreamResult(new File("hoge.xml")));
       		
       		} catch (TransformerException e) {
       		    // 書き出しエラー発生
       		    e.printStackTrace();
       		}
       	}catch(ParserConfigurationException e){
       			System.out.println(e);
       	}catch(SAXException e){
       		System.out.println("not xml");
     		e.printStackTrace();
     	}catch(IOException e){
     		System.out.println(e);
     	}
	}
}
