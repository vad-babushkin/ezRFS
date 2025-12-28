package com.iconiux.ezrfs.tray.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class ClipboardManagerCommandBean {
	private Boolean enable = true;
}

