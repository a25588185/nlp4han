package com.lc.nlp4han.pos.character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import opennlp.tools.ml.model.Event;
import opennlp.tools.util.AbstractEventStream;
import opennlp.tools.util.ObjectStream;

/**
 * 根据上下文得到事件
 * 
 * @author 王馨苇
 * 
 */
public class CharPOSSampleEventStream extends AbstractEventStream<CharPOSSample>
{

    private CharPOSContextGenerator generator;

    /**
     * 构造
     * 
     * @param samples
     *            样本流
     * @param generator
     *            上下文产生器
     */
    public CharPOSSampleEventStream(ObjectStream<CharPOSSample> samples, CharPOSContextGenerator generator)
    {
        super(samples);
        this.generator = generator;
    }

    /**
     * 创建事件
     * 
     * @param sample
     *            样本流
     */
    @Override
    protected Iterator<Event> createEvents(CharPOSSample sample)
    {
        String[] words = sample.getWords();
        String[] poses = sample.getPoses();
        String[] characters = sample.getCharacters();
        String[] tags = sample.getTags();
        String[][] ac = sample.getAditionalContext();
        List<Event> events = generateEvents(characters, tags, words, poses, ac);
        return events.iterator();
    }

    /**
     * 产生事件
     * 
     * @param characters
     *            字符
     * @param tags
     *            字符序列
     * @param words
     *            词语
     * @param poses
     *            词性
     * @param ac
     * @return
     */
    private List<Event> generateEvents(String[] characters, String[] tags, String[] words, String[] poses, String[][] ac)
    {
        List<Event> events = new ArrayList<Event>(characters.length);
        // for (int i = 0; i < words.length; i++) {
        // System.out.print(i+" "+words[i]);
        // }
        for (int i = 0; i < characters.length; i++)
        {
            int record = -1;
            int len = 0;
            for (int j = 0; j < words.length; j++)
            {

                len += words[j].length();
                if ((i + 1) <= len)
                {
                    record = j;
                    break;
                }
            }
            // System.out.print(characters[i]+" "+record);
            // System.out.println();
            // 产生事件的部分
            String[] context = generator.getContext(i, record, characters, tags, words, poses, ac);

            events.add(new Event(tags[i] + "_" + poses[record], context));
        }
        return events;
    }

}