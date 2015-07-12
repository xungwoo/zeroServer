package com.thirtygames.zero.common.etc.document;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.model.columns.DisplayColumn;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.columns.DisplayColumnRepository;
import com.thirtygames.zero.common.model.columns.DisplayColumnRepository.DisplayArea;

@Component
public class DisplayColumnDescriptorFactory {

	@Autowired
	private DisplayColumnRepository displayColumnRepository;

	public List<ColumnDescriptor> create(DisplayArea displayArea,
			List<DisplayColumnId> availableColumnIds) {

		return create(displayArea, availableColumnIds,
				new HashSet<DisplayColumnId>());

	}

	/**
	 * 
	 * 기존 enum을 가지고 사용하는경우
	 * 
	 * @param displayArea
	 * 
	 * @param availableColumnIds
	 * 
	 * @param hiddenColumnIds
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<ColumnDescriptor> create(DisplayArea displayArea,
			List<DisplayColumnId> availableColumnIds,
			Set<DisplayColumnId> hiddenColumnIds) {

		Collection<DisplayColumnId> displayColumnIds = CollectionUtils
				.subtract(availableColumnIds, hiddenColumnIds);

		List<DisplayColumn> displayColumns = displayColumnRepository
				.findColumns(displayArea, displayColumnIds
						.toArray(new DisplayColumnId[displayColumnIds.size()]));

		List<ColumnDescriptor> columnDescriptors = Lists
				.newArrayListWithCapacity(displayColumns.size());

		for (DisplayColumn displayColumn : displayColumns) {

			if (displayColumn != null) {

				columnDescriptors
						.add(new DisplayColumnDescriptor(displayColumn));

			}

		}

		return columnDescriptors;

	}

}
