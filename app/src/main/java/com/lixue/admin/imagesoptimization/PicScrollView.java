package com.lixue.admin.imagesoptimization;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;
import com.lixue.admin.eventdispatch.MyScrollView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.PortUnreachableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PicScrollView extends ScrollView implements View.OnTouchListener {
    private static final String TAG = PicScrollView.class.getSimpleName();

    private static int scrollViewHeight;
    private static View scrollLayout;
    private LinearLayout firstColumn,secondColumn,thirdColumn;
    private int columnWidth;
    private boolean loadOnce;//为了让布局只初始化一次
    private int firstColumnHeight,secondColumnHeight,thirdColumnHeight;

    private int page;//第几页
    private static final int PAGE_SIZE = 15;//一共有几页

    private ImageLoader imageLoader;
    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private static Set<LoadImageTask> taskCollection;

    /**
     * 记录上垂直方向的滚动距离。
     */
    private static int lastScrollY = -1;

    /**
     * 记录所有界面上的图片，用以可以随时控制对图片的释放。
     */
    private List<ImageView> imageViewList = new ArrayList<ImageView>();


    /**
     * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
     */
    private static Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            PicScrollView myScrollView = (PicScrollView) msg.obj;
            int scrollY = myScrollView.getScrollY();
            // 如果当前的滚动位置和上次相同，表示已停止滚动
            if (scrollY == lastScrollY) {
                // 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
                if (scrollViewHeight + scrollY >= scrollLayout.getHeight()
                        && taskCollection.isEmpty()) {
                    myScrollView.loadMoreImages();
                }
                myScrollView.checkVisibility();
            } else {
                lastScrollY = scrollY;
                Message message = new Message();
                message.obj = myScrollView;
                // 5毫秒后再次对滚动位置进行判断
//				进行flipping操作时，ACTION_UP操作后屏幕还未停止滚动，开始5ms监听一次，handler自我发送message循环检测并处理消息
                handler.sendMessageDelayed(message, 5);
            }
        };

    };

    public PicScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageLoader = ImageLoader.getInstance();
        taskCollection = new HashSet<LoadImageTask>();
        setOnTouchListener(this);
    }


    private void initViewAndLoadImages(){
        scrollViewHeight = getHeight();
        scrollLayout = getChildAt(0);
        firstColumn = findViewById(R.id.first_column);
        secondColumn = findViewById(R.id.second_column);
        thirdColumn = findViewById(R.id.third_column);
        columnWidth = firstColumn.getWidth();
        loadOnce = true;
        loadMoreImages();

    }

    /**
     * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载。
     */
    public void loadMoreImages() {
        if (hasSDCard()) {
            int startIndex = page * PAGE_SIZE;
            int endIndex = page * PAGE_SIZE + PAGE_SIZE;
            if (startIndex < Images.imageUrls.length) {
                Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT).show();
                if (endIndex > Images.imageUrls.length) {
                    endIndex = Images.imageUrls.length;
                }
                for (int i = startIndex; i < endIndex; i++) {
                    LoadImageTask task = new LoadImageTask();
                    taskCollection.add(task);
                    task.execute(i);
                }
                page++;
            } else {
                Toast.makeText(getContext(), "已没有更多图片", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 遍历imageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图。
     */
    public void checkVisibility() {
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            int borderTop = (Integer) imageView.getTag(R.string.border_top);
            int borderBottom = (Integer) imageView.getTag(R.string.border_bottom);
            if (borderTop >= getScrollY() + scrollViewHeight) {
                Log.v(TAG, "borderTop:"+borderTop+"     getScrollY() + scrollViewHeight:"+getScrollY() +"  "+ scrollViewHeight+"该图片要被隐藏");
            }

            if (borderBottom > getScrollY() && borderTop < getScrollY() + scrollViewHeight) {
                String imageUrl = (String) imageView.getTag(R.string.image_url);
                Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    LoadImageTask task = new LoadImageTask(imageView);
                    task.execute(i);
                }
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    private boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 进行一些关键性的初始化操作，获取PicScrollView的高度
     * 以及得到第一列的宽度值。并在这里开始加载第一页的图片
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!loadOnce && changed){
            initViewAndLoadImages();
        }
    }

    /**
     * 向imageview中添加一张图片
     * @param bitmap
     * @param imageWidth
     * @param imageHeight
     */
    private void addImage(Bitmap bitmap,int imageWidth,int imageHeight){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth,imageHeight);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setPadding(5,5,5,5);

        findColumnToAdd(imageView,imageHeight).addView(imageView);
    }

    /**
     * 找出此时应该添加图片的一列
     * 原则就是对三列高度进行判断，当前高度最小的那一列
     * @param imageView
     * @param imageHeight
     * @return
     */
    private LinearLayout findColumnToAdd(ImageView imageView, int imageHeight) {
        if (firstColumnHeight <= secondColumnHeight){
            if (firstColumnHeight <= thirdColumnHeight){
                firstColumnHeight += imageHeight;
                return firstColumn;
            }
            thirdColumnHeight =+ imageHeight;
            return thirdColumn;
        }else {
            if (secondColumnHeight <= thirdColumnHeight){
                secondColumnHeight += imageHeight;
                return secondColumn;
            }
            thirdColumnHeight =+ imageHeight;
            return thirdColumn;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v(TAG, "onTouch");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Message message = new Message();
            message.obj = this;
            handler.sendMessageDelayed(message, 5);
        }
        return false;
    }

    class LoadImageTask extends AsyncTask<Integer,Void, Bitmap>{
        /**
         * 记录每个图片对应的位置
         */
        private int mItemPosition;

        /**
         * 图片的URL地址
         */
        private String mImageUrl;

        /**
         * 可重复使用的ImageView
         */
        private ImageView mImageView;
        public LoadImageTask(){}
        /**
         * 将可重复使用的ImageView传入
         *
         * @param imageView
         */
        public LoadImageTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            mItemPosition = params[0];
            mImageUrl = Images.imageUrls[mItemPosition];
            Bitmap imageBitmap = imageLoader.getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
                imageBitmap = loadImage(mImageUrl);
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                //核心代码，新建一个imageview,imageview宽度是相同的(屏幕的1/3）
                //这样图片才不会失真，然后把imageview添加到屏幕上显示
                double ratio = bitmap.getWidth() / (columnWidth * 1.0);
                int scaledHeight = (int) (bitmap.getHeight() / ratio);
                addImage(bitmap,columnWidth,scaledHeight);
            }
            taskCollection.remove(this);
        }

        /**
         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡里读取，否则就从网络上下载。
         *
         * @param imageUrl
         *            图片的URL地址
         * @return 加载到内存的图片。
         */
        private Bitmap loadImage(String imageUrl) {
            File imageFile = new File(getImagePath(imageUrl));
            if (!imageFile.exists()) {
                downloadImage(imageUrl);
            }
            if (imageUrl != null) {
                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(imageFile.getPath(),columnWidth);
                if (bitmap != null) {
                    imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
                    return bitmap;
                }
            }
            return null;
        }

        /**
         * 向ImageView中添加一张图片
         *
         * @param bitmap
         *            待添加的图片
         * @param imageWidth
         *            图片的宽度
         * @param imageHeight
         *            图片的高度
         */
        private void addImage(Bitmap bitmap, int imageWidth, int imageHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth,
                    imageHeight);
            if (mImageView != null) {
                mImageView.setImageBitmap(bitmap);
            } else {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setTag(R.string.image_url, mImageUrl);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ImageDetailsActivity.class);
                        intent.putExtra("image_position", mItemPosition);
                        getContext().startActivity(intent);
                    }
                });
                findColumnToAdd(imageView, imageHeight).addView(imageView);
                imageViewList.add(imageView);
            }
        }

        /**
         * 找到此时应该添加图片的一列。原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加的一列。
         *
         * @param imageView
         * @param imageHeight
         * @return 应该添加图片的一列
         */
        private LinearLayout findColumnToAdd(ImageView imageView, int imageHeight) {
            if (firstColumnHeight <= secondColumnHeight) {
                if (firstColumnHeight <= thirdColumnHeight) {
                    imageView.setTag(R.string.border_top, firstColumnHeight);
                    firstColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom, firstColumnHeight);
                    return firstColumn;
                }
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom, thirdColumnHeight);
                return thirdColumn;
            } else {
                if (secondColumnHeight <= thirdColumnHeight) {
                    imageView.setTag(R.string.border_top, secondColumnHeight);
                    secondColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_bottom, secondColumnHeight);
                    return secondColumn;
                }
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_bottom, thirdColumnHeight);
                return thirdColumn;
            }
        }

        /**
         * 将图片下载到SD卡缓存起来。
         *
         * @param imageUrl
         *            图片的URL地址。
         */
        private void downloadImage(String imageUrl) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.d("TAG", "monted sdcard");
            } else {
                Log.d("TAG", "has no sdcard");
            }
            HttpURLConnection con = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File imageFile = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(15 * 1000);
                con.setDoInput(true);
//                con.setDoOutput(true);  post方式请求
                con.setRequestMethod("GET") ;
                con.setRequestProperty(
                        "Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, " +
                                "application/x-shockwave-flash, application/xaml+xml, " +
                                "application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                                "application/x-ms-application, application/vnd.ms-excel, " +
                                "application/vnd.ms-powerpoint, application/msword, */*");
                con.setRequestProperty("Accept-Language", "zh-CN");
                con.setRequestProperty("Charset", "UTF-8");
                bis = new BufferedInputStream(con.getInputStream());
                imageFile = new File(getImagePath(imageUrl));
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                byte[] b = new byte[1024];
                int length;
                while ((length = bis.read(b)) != -1) {
                    bos.write(b, 0, length);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (con != null) {
                        con.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (imageFile != null) {
                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(imageFile.getPath(),
                        columnWidth);
                if (bitmap != null) {
                    imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
                }
            }
        }

        /**
         * 获取图片的本地存储路径。
         *
         * @param imageUrl
         *            图片的URL地址。
         * @return 图片的本地存储路径。
         */
        private String getImagePath(String imageUrl) {
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            String imageName = imageUrl.substring(lastSlashIndex + 1);
            String imageDir = Environment.getExternalStorageDirectory().getPath()
                    + "/PhotoWallFalls/";
            File file = new File(imageDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String imagePath = imageDir + imageName;
            return imagePath;
        }
    }
}
