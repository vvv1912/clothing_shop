curl --location --request POST 'http://47.57.106.86/yuenan-interface-web/jtpos/inquiry!freight.action' \
--form 'logistics_interface="{\"selfAddress\":1,\"cusname\":\"084LC012345\",\"goodsvalue\":\"100000\",\"itemsvalue\":\"44444\",\"weight\":\"1\",\"sender\":{\"prov\":\"3\",\"city\":\"799\",\"area\":\"18135\"},\"receiver\":{\"prov\":\"3\",\"city\":\"799\",\"area\":\"18140\"},\"feetype\":\"CHARGE\",\"producttype\":\"EZ\"}"' \
--form 'data_digest="Mzc1MDFkNGExMjk3NzY4YjYyYmM2NmVlOTM2YTI4MmE="' \
--form 'msg_type="FREIGHTQUERY"' \
--form 'eccompanyid="CUSMODEL"'