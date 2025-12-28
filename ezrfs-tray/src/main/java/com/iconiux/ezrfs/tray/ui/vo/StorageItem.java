package com.iconiux.ezrfs.tray.ui.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@Accessors(chain = true)
@FieldNameConstants(asEnum = true)
public class StorageItem {
	@JsonProperty("uploadedDateTime")
	protected Date uploadedDateTime;

	@JsonProperty("fileName")
	protected String fileName;

	@JsonProperty("sizeHumanize")
	protected String sizeHumanize;

	@JsonProperty("url")
	protected String url;
}
