package jp.ac.jec.cm0128.soundgame;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> arr=new ArrayList<Integer> ();
    private int[] imgBtn={
            R.id.imageButton1,
            R.id.imageButton2,
            R.id.imageButton3,
            R.id.imageButton4,
            R.id.imageButton5,
            R.id.imageButton6,
            R.id.imageButton7,
            R.id.imageButton8,
    };
    private int katu=0;

    private MediaPlayer[] players= new MediaPlayer[imgBtn.length];

    private int[] answr = new int[imgBtn.length];
    private int nowState =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        findViewById (R.id.button).setOnClickListener (new startBtn ());

        for(int id:imgBtn){
            findViewById (id).setOnClickListener (new BtnClick ());
        }
        initGame();
    }
    private  void initGame(){
        int[] setItem={R.raw.se_door01,R.raw.se_phone02,R.raw.se_whistle01,R.raw.se_drink02};
        //答え初期化
        for (int i=0;i<answr.length;i++){
            answr[i]=-1;
        }

        for (int i=0;i<2;i++){//两个同样的音乐设定
            for (int k=0;k<setItem.length;k++){
                int rand =(int)(Math.random ()*answr.length);
                if (answr[rand]!=-1){
                    k--;
                    continue;
                }
                answr[rand]=setItem[k];
            }
        }
//        割り当てられた音を生成
        for (int i=0;i<players.length;i++){
            players[i]=MediaPlayer.create (this,answr[i]);
        }




//        for (int i =1;i<players.length;i++){
//            if (i%2==0){
////                插入音乐
//                players[i]=MediaPlayer.create (this,R.raw.se_whistle01);
//            }else{
//                players[i]=MediaPlayer.create (this,R.raw.se_phone02);
//            }
//
//        }

    }
    private ImageButton preSelect=null;

    class BtnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            //创建音乐
//            MediaPlayer mediaParser = MediaPlayer.create(MainActivity.this,R.raw.se_ok);
//            //设置
//            mediaParser.setLooping (false);//循环播放
//            mediaParser.seekTo (0);//参数是毫秒
//            mediaParser.start ();

            int i=0;
            for (i=0;i<imgBtn.length;i++){
                if (v.getId ()==imgBtn[i]){
                    break;
                }
            }
            MediaPlayer play=players[i];

            play.setLooping (false);
            play.seekTo (0);
            play.start ();

//            设置图片和答对提示、
            TextView txt =(TextView)findViewById (R.id.textView);


            if (nowState==-1){
                txt.setText ("二つ目を選択くしてください");
                nowState=answr[i];
                preSelect = ((ImageButton)v);

                ((ImageButton)v).setEnabled (false);
                preSelect.setEnabled (false);
                ((ImageButton)v).setImageResource(R.drawable.natsu_folderopen);
                preSelect.setImageResource(R.drawable.natsu_folderopen);

                    arr.add ( ((ImageButton)v).getId ());
                   Log.i ("", "添加了第一个: "+((ImageButton)v).getId ());

            }else{
                katu++;
                switch (katu){
                    case 6:
                        Toast.makeText (MainActivity.this,"一杯いかが？！！",Toast.LENGTH_SHORT).show ();
                        break;
                    case 12:
                        Toast.makeText (MainActivity.this,"飲みませんか？！！",Toast.LENGTH_SHORT).show ();
                        break;
                    case 22:
                        Toast.makeText (MainActivity.this,"まだまだですね！！",Toast.LENGTH_SHORT).show ();
                        break;
                    case 18:
                        Toast.makeText (MainActivity.this,"今夜はカップ麺だ？！！",Toast.LENGTH_SHORT).show ();
                        break;
                    default:
                }
                if (

                        nowState==answr[i]){
                    //创建音乐
                    MediaPlayer mediaParser = MediaPlayer.create(MainActivity.this,R.raw.se_ok);
                    //设置
                    mediaParser.setLooping (false);//循环播放
                    mediaParser.seekTo (0);//参数是毫秒
                    mediaParser.start ();
                    //正确的添加到数组里面

                    arr.add ( ((ImageButton)v).getId ());
                    Log.i ("", "添加了第二个: "+((ImageButton)v).getId ());

                    ((ImageButton)v).setEnabled (false);
                    preSelect.setEnabled (false);
                    ((ImageButton)v).setImageResource(R.drawable.natsu_folderopen);
                    preSelect.setImageResource(R.drawable.natsu_folderopen);
                    txt.setText ("正解");
                    nowState=-1;

                    if (katu==8&&arr.size ()==8){
                        txt.setText ("全て正解まで"+katu+"回おしました。");
                        Toast.makeText (MainActivity.this,"素晴らしい！！",Toast.LENGTH_SHORT).show ();
                    }
                    if (katu>8&&arr.size ()==8){
                        txt.setText ("全て正解まで"+katu+"回おしました。");
                        Toast.makeText (MainActivity.this,"より一層の西進すべし！！",Toast.LENGTH_SHORT).show ();
                    }

                    Log.i ("正解", "onClick: ");
                }else{
                    txt.setText ("不正解、一つ目を選択してください");
//                    第一个不正确的时候清理arr
//                    arr大于2的时候在走判断

                    switch (arr.size ()){
                        case 2:
                        case 4:
                        case 6:
                            restBtn();
                            break;
                        case 3:
                        case 5:
                        case 7:
                            Log.i ("删除倒数第一个", "onClick: "+arr);
                            arr.remove(arr.get (arr.size ()-1));
                            Log.i ("*删除倒数第一个", "onClick: "+arr);
                            restBtn();
                            break;
                        case 8:

                        default:
                            Log.i ("清理了", "clear: "+arr);
                            arr.clear ();
                            for (int ex:imgBtn){
                                ImageButton btnId = (ImageButton)findViewById (ex);
                                btnId.setEnabled (true);
                                btnId.setImageResource (R.drawable.natsu_folder);
                                btnId.setImageResource (R.drawable.natsu_folder);
                            }
                    }

                    nowState=-1;
                    //创建音乐
                    MediaPlayer mediaParser = MediaPlayer.create(MainActivity.this,R.raw.se_ng);
                    //设置
                    mediaParser.setLooping (false);//循环播放
                    mediaParser.seekTo (0);//参数是毫秒
                    mediaParser.start ();
                }
            }

        }
    }
    class startBtn implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            initGame();
            katu=0;
            TextView txt =(TextView)findViewById (R.id.textView);
            txt.setText ("");
            nowState=-1;
            for(int id:imgBtn) {
                ImageButton btnId = (ImageButton)findViewById (id);
                btnId.setEnabled (true);
                btnId.setImageResource (R.drawable.natsu_folder);
                preSelect.setImageResource (R.drawable.natsu_folder);
            }
        }
    }
    private void restBtn(){
        Log.i ("不正解、一つ目を選択してください", "onClick: "+arr);
        for (int b=0;b<imgBtn.length;b++){
            ImageButton btnId = (ImageButton)findViewById (imgBtn[b]);
            btnId.setEnabled (true);
            btnId.setImageResource (R.drawable.natsu_folder);
            btnId.setImageResource (R.drawable.natsu_folder);
            for (int s=0;s<arr.size ();s++){
                if (arr.get (s)==btnId.getId()){
                    Log.i ("yyy清理了"+arr.get (s), "clear: "+btnId.getId());
                    btnId.setEnabled (false);
                    btnId.setImageResource (R.drawable.natsu_folderopen);
                    btnId.setImageResource (R.drawable.natsu_folderopen);
                }
            }
        }
    }
}