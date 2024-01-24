package com.takeo.service;

import java.io.OutputStream;

import com.takeo.dto.ReportDto;

public interface ReportService {
	public void generatePdfReport(ReportDto data, OutputStream outputStream);

	public void generateExcelReport(ReportDto data, OutputStream outputStream);

}
