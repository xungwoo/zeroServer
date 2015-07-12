package com.thirtygames.zero.admin.ftl;
import java.util.ArrayList;
import java.util.List;

public class Action {
	List<String> errors = new ArrayList<String>();
	List<String> messages = new ArrayList<String>();
	List<String> infos = new ArrayList<String>();
	List<String> warnings = new ArrayList<String>();
	List<String> jsFiles = new ArrayList<String>();
	List<String> cssFiles = new ArrayList<String>();
	List<Integer> pages = new ArrayList<Integer>();
	Integer currentPage = 0;
	Integer prevPage = 0;
	Integer nextPage = 0;
	Boolean prevEnable = true;
	Boolean nextEnable = true;
	String title ="";

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getErrors() {
		return errors;
	}
	public List<String> getMessages() {
		return messages;
	}
	public List<String> getInfos() {
		return infos;
	}
	public List<String> getWarnings(){
		return warnings;
	}
	public List<String> getJsFiles() {
		return jsFiles;
	}
	public List<Integer> getPages() {
		return pages;
	}
	public void addError( String error ) {
		errors.add( error );
	}
	public void addMessage( String message ) {
		messages.add( message );
	}
	public void addInfo( String info ){
		infos.add( info );
	}
	public void addWarning( String wanring ){
		warnings.add( wanring );
	}
	public void addJsFile( String file ){
		jsFiles.add( file );
	}
	public void addCssFile( String file ){
		cssFiles.add( file );
	}
	public void addPage( Integer page ){
		pages.add( page );
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public Boolean getPrevEnable() {
		return prevEnable;
	}
	public void setPrevEnable(Boolean prevEnable) {
		this.prevEnable = prevEnable;
	}
	public Boolean getNextEnable() {
		return nextEnable;
	}
	public void setNextEnable(Boolean nextEnable) {
		this.nextEnable = nextEnable;
	}
	public List<String> getCssFiles() {
		return cssFiles;
	}
	public void setCssFiles(List<String> cssFiles) {
		this.cssFiles = cssFiles;
	}
}
