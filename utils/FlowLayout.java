import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import cn.moocollege.QstApp.R;
import cn.moocollege.QstApp.utils.DisplayUtils;

public class FlowLayout extends ViewGroup {

	private static final int DEFAULT_HORIZONTAL_SPACING = 5;          //默认内部控件之间横向间距
	private static final int DEFAULT_VERTICAL_SPACING = 5;				//默认内部控件之间纵向间距
	
	private int mVerticalSpacing;       //控件之间横向间距
	private int mHorizontalSpacing;     //控件之间纵向间距
	
	public FlowLayout(Context context) {
		super(context);
	}

	public FlowLayout(Context context, AttributeSet attrs){
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);              //获取xml文件中得到的参数
		try {
			mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontal_spacing, DisplayUtils.getInstance().dp2px(DEFAULT_HORIZONTAL_SPACING));   //获取内部控件之间横向间距
			mVerticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_vertical_spacing, DisplayUtils.getInstance().dp2px(DEFAULT_VERTICAL_SPACING));	 //获取内部空间之间纵向间距
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			a.recycle();
		}
	}
	
	/**
	 * 动态设置横向间距
	 * @param pixelSize  像素值
	 */
	
	public void setHorizontalSpacing(int pixelSize){
		mHorizontalSpacing = pixelSize;          
	}
	/**
	 * 动态设置纵向间距
	 * @param pixelSize
	 */
	public void setVerticalSpacing(int pixelSize){
		mVerticalSpacing = pixelSize; 
	}
	
	/**
	 * 设置内部控件布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int mWidth = r - l;
		
		int paddingLeft = getPaddingLeft();        //父控件左缩进
		int paddingRight = getPaddingRight();    //父控件右缩进
		int paddingTop = getPaddingTop();		//父控件上缩进
		
		int childLeft = paddingLeft;        //子空件距离父控件左边界距离
		int childTop = paddingTop;          //子控件距离父控件上边界距离
		
		int lineHeight = 0;    //获得某行控件最高高度
		
		for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
			View childView = getChildAt(i);
			if (childView.getVisibility() == View.GONE) {
				continue;
			}
			
			int childWidth = childView.getMeasuredWidth();      //获得子控件宽度
			int childHeight = childView.getMeasuredHeight();    //获得子控件高度
			
			lineHeight = Math.max(childHeight, lineHeight);      //获取本行最大高度
			
			if (childLeft + childWidth + paddingRight > mWidth) { //若此行无法显示则换行并更新左、上边距以及初始化做高控件高度
				childLeft = paddingLeft;
				childTop += mVerticalSpacing + lineHeight;
				lineHeight = childHeight;
			}
			
			childView.layout(childLeft, childTop, childLeft + childWidth,  childTop + childHeight);   //设置子控件布局
			childLeft += (childWidth + mHorizontalSpacing);		//设置控件右端间隔
		}
	}
	
	/**
	 * 测量控件所需宽度和高度
	 */
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int mWidth = resolveSize(0, widthMeasureSpec);	//获取父控件宽度
		int paddingLeft = getPaddingLeft();				//父控件左缩进
		int paddingTop = getPaddingTop();				//父控件上缩进
		int paddingRight = getPaddingRight();			//父控件右缩进
		int paddingBottom = getPaddingBottom();			//父控件下缩进
		
		int childLeft = paddingLeft;				//子空件距离父控件左边界距离
		int childTop = paddingTop;					//子控件距离父控件上边界距离
		
		int lineHeight = 0;
		
		for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				measureChild(child, widthMeasureSpec, widthMeasureSpec);     //递归测量子控件
			}
			else {
				continue;
			}
			
			int childWidth = child.getMeasuredWidth();      //获取子控件宽度
			int childHeight = child.getMeasuredHeight();	//获取子控件高度
			
			lineHeight = Math.max(lineHeight, childHeight);		//本行最高子控件高度
			
			if (childLeft + childWidth + paddingRight > mWidth) {   //子控件超过父控件宽度则换行
				childLeft = paddingLeft;
				childTop += mVerticalSpacing + lineHeight;
				lineHeight = childHeight;
			}
			else {
				childLeft += (childWidth + mHorizontalSpacing);   	//更新子控件左边距
			}
		}
		
		int wantedHeight = childTop + lineHeight + paddingBottom;		//获取测量到的所需控件高度
		
		setMeasuredDimension(mWidth, resolveSize(wantedHeight, heightMeasureSpec));  //设置测量结果
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
