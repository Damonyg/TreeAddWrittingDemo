package com.example.treeaddwritedemo.global;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.treeaddwritedemo.util.Category;
import com.example.treeaddwritedemo.util.CloudAlbumData;
import com.example.treeaddwritedemo.util.CloudAlbumImg;
import com.example.treeaddwritedemo.util.CloudAlbumImgs;
import com.example.treeaddwritedemo.util.CloudAlbumUser;
import com.example.treeaddwritedemo.util.Subcate;
import com.example.treeaddwritedemo.util.Tree;
import com.example.treeaddwritedemo.util.TreeCategory;
import com.example.treeaddwritedemo.util.TreeData;

/***
 * 解析工具类
 * 
 * @author John
 * 
 */
public class ParseUtil {
	/***
	 * 解析树Data
	 */
	public static String onJsonData(String mJson) {
		JSONObject mJsonObj;
		try {
			mJsonObj = new JSONObject(mJson);
			int errCode = mJsonObj.optInt("errCode");
			if (errCode == 0)
				return mJsonObj.optString("data");
			else
				return "errCode";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "errCode";
		}

	}

	/***
	 * 解析树Data
	 */
	public static TreeData onJsonTreeData(String mJson) {
		TreeData mData = new TreeData();
		try {
			JSONArray mJsonAry = new JSONArray(mJson);
			if (mJsonAry != null && mJsonAry.length() > 0) {
				int length = mJsonAry.length();
				List<Tree> mTree = new ArrayList<Tree>();
				for (int i = 0; i < length; i++) {
					mTree.add(onJsonTrees(mJsonAry.optJSONObject(i)));
				}
				mData.setData(mTree);
			} else {
				mData.setData(new ArrayList<Tree>());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mData;

	}

	/***
	 * 解析Img
	 */
	public static Tree onJsonTrees(JSONObject mJsonObj) {
		Tree mTree = new Tree();
		if (mJsonObj != null) {
			mTree.setAvatar(mJsonObj.optString("avatar"));
			mTree.setId(mJsonObj.optInt("id"));
			mTree.setOrder(mJsonObj.optString("order"));
			mTree.setIs_main(mJsonObj.optString("is_main"));
			mTree.setIs_owner(mJsonObj.optString("is_owner"));
			mTree.setNum_member(mJsonObj.optString("num_member"));
			mTree.setLabel(mJsonObj.optString("label"));
			mTree.setDisplay_name(mJsonObj.optString("display_name"));
			mTree.setCover(mJsonObj.optString("cover"));
			mTree.setType(mJsonObj.optString("type"));
			mTree.setNum_image(mJsonObj.optString("num_image"));
		}
		return mTree;
	}

	/***
	 * 解析云相册Data
	 */
	public static CloudAlbumData onJsonCloudAlbumData(String mJson) {
		CloudAlbumData mData = new CloudAlbumData();
		if (mJson != null) {
			JSONObject mJsonObj;
			try {
				mJsonObj = new JSONObject(mJson);
				mData.setTotal(mJsonObj.optInt("total"));
				mData.setImg_path(mJsonObj.optString("img_path"));
				mData.setAvatar_path(mJsonObj.optString("avatar_path"));
				JSONArray mJsonAry = mJsonObj.optJSONArray("imgs");
				if (mJsonAry != null && mJsonAry.length() > 0) {
					int length = mJsonAry.length();
					ArrayList<CloudAlbumImg> mImg = new ArrayList<CloudAlbumImg>();
					for (int i = 0; i < length; i++) {
						mImg.add(onJsonImg(mJsonAry.optJSONObject(i)));
					}
					ArrayList<CloudAlbumImgs> mImgs = new ArrayList<CloudAlbumImgs>();
					mImgs = onJsonImgs(mImg);
					mData.setImgs(mImgs);
				} else {
					mData.setImgs(new ArrayList<CloudAlbumImgs>());
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mData;
	}
	
	/***
	 * 解析Imgs
	 */
	public static ArrayList<CloudAlbumImgs> onJsonImgs(ArrayList<CloudAlbumImg> mImg) {
		ArrayList<CloudAlbumImgs> mImgs = new ArrayList<CloudAlbumImgs>();
		if (mImg != null) {
			CloudAlbumImgs temp = null;
			for(CloudAlbumImg mItem:mImg){
				if(temp == null || !temp.getDate().equals(mItem.getDate())){
					temp = new CloudAlbumImgs();
					temp.setDate(mItem.getDate());
					temp.setPlace(mItem.getPlace());
					mImgs.add(temp);
				}
				temp.getImgs().add(mItem);
			}
			

		}
		return mImgs;
	}


	/***
	 * 解析Img
	 */
	public static CloudAlbumImg onJsonImg(JSONObject mJsonObj) {
		CloudAlbumImg mImg = new CloudAlbumImg();
		if (mJsonObj != null) {
			try {
				mImg.setStatus(mJsonObj.optInt("status"));
				mImg.setOwner(mJsonObj.optBoolean("owner"));
				mImg.setId(mJsonObj.optString("id"));
				mImg.setFile(mJsonObj.optString("file"));
				mImg.setDate(mJsonObj.optString("date"));
				mImg.setThumb(mJsonObj.optString("thumb"));
				mImg.setPlace(mJsonObj.optString("place"));
				mImg.setUser(onJsonUser(mJsonObj.getJSONObject("user")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return mImg;
	}

	/***
	 * 解析User
	 */
	public static CloudAlbumUser onJsonUser(JSONObject mJsonObj) {
		CloudAlbumUser mUser = new CloudAlbumUser();
		if (mJsonObj != null) {
			mUser.setAvatar(mJsonObj.optString("avatar"));
			mUser.setId(mJsonObj.optString("id"));
			mUser.setDisplay_name(mJsonObj.optString("display_name"));

		}
		return mUser;
	}

	/***
	 * 解析TreeCategory
	 */
	public static TreeCategory onJsonTreeCategory(JSONObject mJsonObj) {
		TreeCategory mTreeCategory = new TreeCategory();
		if (mJsonObj != null) {
			mTreeCategory.setTree_id(mJsonObj.optInt("tree_id"));
			JSONArray mJsonAry = mJsonObj.optJSONArray("categories");
			if (mJsonAry != null && mJsonAry.length() > 0) {
				int length = mJsonAry.length();
				ArrayList<Category> mCategorys = new ArrayList<Category>();
				for (int i = 0; i < length; i++) {
					mCategorys.add(onJsonCategory(mJsonAry.optJSONObject(i)));
				}
				mTreeCategory.setCategories(mCategorys);
			} else {
				mTreeCategory.setCategories(new ArrayList<Category>());
			}
		}
		return mTreeCategory;
	}

	/***
	 * 解析Category
	 */
	public static Category onJsonCategory(JSONObject mJsonObj) {
		Category mCategory = new Category();
		if (mJsonObj != null) {
			mCategory.setCate_id(mJsonObj.optInt("cate_id"));
			JSONArray mJsonAry = mJsonObj.optJSONArray("subcates");
			if (mJsonAry != null && mJsonAry.length() > 0) {
				int length = mJsonAry.length();
				List<Subcate> mSubcates = new ArrayList<Subcate>();
				for (int i = 0; i < length; i++) {
					mSubcates.add(onJsonSubcate(mJsonAry.optJSONObject(i)));
				}
				mCategory.setSubcates(mSubcates);
			} else {
				mCategory.setSubcates(new ArrayList<Subcate>());
			}
		}
		return mCategory;
	}

	/***
	 * 解析Category
	 */
	public static Subcate onJsonSubcate(JSONObject mJsonObj) {
		Subcate mSubcate = new Subcate();
		if (mJsonObj != null) {
			mSubcate.setId(mJsonObj.optString("id"));
			mSubcate.setDate(mJsonObj.optString("date"));
			mSubcate.setName(mJsonObj.optString("name"));
		}
		return mSubcate;
	}
}
