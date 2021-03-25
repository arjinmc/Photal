# Photal
An android plugin library to select photos,take a photo and make photo effects. 

Depends on:
* [Glide](https://github.com/bumptech/glide)
* [Ucrop](https://github.com/Yalantis/uCrop)
* [PhotoView](https://github.com/Baseflow/PhotoView)
* [ExpandRecyclerView](https://github.com/arjinmc/ExpandRecyclerView)

## Call Selector for photos
### Multiple Selector
```code
Photal.getInstance().startMultipleSelector(context
    , resultCode, bundle_key_for_result_images, max_selected_count);
```
in onActivityResult()
```code
List<MediaFileItem> mediaFileItemList = data.getParcelableArrayListExtra(bundle_key_for_result_images);
if (mediaFileItemList == null || mediaFileItemList.isEmpty()) {
    Log.e("path", "empty");
} else {
    for (MediaFileItem mediaFileItem : mediaFileItemList) {
        Log.e("path", "path:" + mediaFileItem.getPath()
                + "\nuri:" + mediaFileItem.getUriPath()
                + "\noriginal uri:" + mediaFileItem.getUriOriginalPath()
                + "\nmimeType:" + mediaFileItem.getMimeType()
                + "\nsize:" + mediaFileItem.getSize()
                + "\ndateTaken:" + mediaFileItem.getDateTaken()
        );
    }
}
```
### Single Selector
```code 
Photal.getInstance().startSingleSelector(context, resultCode, bundle_key_for_result_images);
```
in onActivityResult() as Multiple Selector

## Call system camera
```code
Photal.getInstance().capture(context, requestCode, file);
```

## Also works with UCrop
```code
Photal.getInstance().crop(context, mFile.getAbsolutePath(), mCropFile.getAbsolutePath(), size);
```

## Config
You can change all these style as you need.
```code
PhotalConfig photalConfig = new PhotalConfig(context);
photalConfig.setThemeColor(ContextCompat.getColor(this, R.color.colorAccent));
photalConfig.setThemeDarkColor(ContextCompat.getColor(this, R.color.red));
photalConfig.setTextTitleSize(R.dimen.text_title);
photalConfig.setTextTitleColor(ContextCompat.getColor(this, R.color.colorAccent));
photalConfig.setBtnBackIcon(android.R.drawable.ic_menu_more);
photalConfig.setBtnDoneBackground(R.drawable.btn_done);
photalConfig.setBtnDoneTextColor(ContextCompat.getColor(this, R.color.colorAccent));
photalConfig.setBtnDoneTextSize(R.dimen.text_send);
photalConfig.setAlbumBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
photalConfig.setAlbumTextSize(R.dimen.text_send);
photalConfig.setAlbumTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
photalConfig.setAlbumCheckBox(R.drawable.cb_album);
photalConfig.setAlbumDiver(new RecyclerViewLinearItemDecoration.Builder(this)
        .color(ContextCompat.getColor(this, com.arjinmc.photal.R.color.photal_album_background))
        .thickness(2).create());
photalConfig.setGalleryDiver(new RecyclerViewGridItemDecoration.Builder(this)
        .color(ContextCompat.getColor(this, R.color.photal_send_disable))
        .horizontalSpacing(2)
        .verticalSpacing(2)
        .create());
photalConfig.setGalleryColumnCount(4);
photalConfig.setGalleryCheckboxColor(Color.YELLOW);
photalConfig.setGalleryBackgroundColor(Color.WHITE);
photalConfig.setPreviewCheckbox(R.drawable.cb_album);
photalConfig.setPreviewTextColor(Color.RED);
photalConfig.setPreviewTextSize(R.dimen.text_send);
photalConfig.setCropDoneIcon(R.drawable.photal_ic_crop_done);
//you must change it 
photalConfig.setFileProviderAuthorities("com.arjinmc.photal.fileprovider");
//if you need to access GPS info from images
photalConfig.setAccessGPS(true);

Photal.getInstance().setConfig(photalConfig);
```

## License
```code 
   Copyright 2017 arjinmc

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```