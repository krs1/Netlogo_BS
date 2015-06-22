.nlogoファイルの中身（変数）を編集するプログラム

一度BehaviorSpaceをやっておく必要がある

UTF-8で書いているのでコンパイルは

javac -encoding UTF-8 file.java
とかじゃないとエラー吐くかも

実行すると

編集したいファイル名(.nlogo)
入力する変数の数
変数の名前
変数の値
の順に入力する。

このファイルを実行する場所と同じ場所に.nlogoファイルを配置しておくことを想定している



使用例：

home/netlogo/Fire.nlogoがあるとする。

javac -encoding UTF-8 file.java

java file



file_name:Fire.nlogo
imput number of prams:1
imput pram_name:density
imput pram value:75


木の密度を75%に置き換える


注意点
2個以上変数がある場合変数の順番通りに入力してください。