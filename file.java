import java.io.*;
import java.util.*;//ArrayListとScaner
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
			 // file_Edit(file,para_name,para_number);
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
		int i=0;
		try{
			BufferedReader br = new BufferedReader(new FileReader(file_name));
			String str = null;
			while((str=br.readLine()) != null){//1行読み込み
				 if(str.equals("<experiments>")){//<experiments>に当たったら別の処理
				 	fileRead.append(str+"\r\n");
				 	System.out.println(str);
				 	while(!(str=br.readLine()).equals("</experiments>")){//</experiments>までループ
				 		if(name.size()>i){
							if(str.equals("    <enumeratedValueSet variable=\""+name.get(i)+"\">")){//name内の文字列比較
								fileRead.append(str+"\r\n");//上の文字列をそのまま追加
								System.out.println(str);
								br.readLine();//読み込み
								str="      <value value=\""+number.get(i)+"\"/>\r\n";
								fileRead.append(str);//文字列の書き換え
								System.out.print(str);
								i++;//下の変数の更新
							}else {
								fileRead.append(str+"\r\n");
								System.out.println(str);
								try{
									Thread.sleep(1000); //3000ミリ秒Sleepする
								}catch(InterruptedException e){}
							}
						}else{
							fileRead.append(str+"\r\n");
							System.out.println(str);
						}
						
				 	}
				 }
				fileRead.append(str+"\r\n");
				System.out.println(str);
			}
			br.close();
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
}
