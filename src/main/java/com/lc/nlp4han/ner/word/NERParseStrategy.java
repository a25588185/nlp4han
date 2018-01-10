package com.lc.nlp4han.ner.word;

/**
 * 策略接口
 * @author 王馨苇
 *
 */
public interface NERParseStrategy {

	/**
	 * 为基于字的命名实体解析文本
	 * @param sentence
	 * @return
	 */
	public NERWordOrCharacterSample parse(String sentence);
	
}
