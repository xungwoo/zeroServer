package com.thirtygames.zero.common.etc.document.result;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import com.thirtygames.zero.common.etc.document.writer.SheetWriter;

public class WorksheetResultConvertingHandler<E, T> implements ResultHandler {

	protected SheetWriter sheetWriter;

	protected RowConverter<E, T> rowConverter;

	public WorksheetResultConvertingHandler(SheetWriter sheetWriter, RowConverter<E, T> rowConverter) {
		this.sheetWriter = sheetWriter;
		this.rowConverter = rowConverter;
	}

	/**
	 * @param context
	 * @see org.apache.ibatis.session.ResultHandler#handleResult(org.apache.ibatis.session.ResultContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handleResult(ResultContext context) {
		sheetWriter.writeRowEntry(context.getResultCount(), rowConverter.convert((T)context.getResultObject()));
	}

}
