package com.iconiux.ezrfs.tray.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
//@FieldNameConstants
public class ProgressInfoMessageBean {
	private int progress;
}
