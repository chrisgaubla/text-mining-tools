package icdtextminer;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;

import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceChunker;
import com.aliasi.sentences.SentenceModel;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.Set;

/**
 * Use SentenceModel to find sentence boundaries in text
 */
public class SentenceSplitter {

    static final TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
    static final SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
    static final SentenceChunker SENTENCE_CHUNKER= new SentenceChunker(TOKENIZER_FACTORY, SENTENCE_MODEL);

    public ArrayList<String> getChunk(String text) {

        Chunking chunking = SENTENCE_CHUNKER.chunk(text.toCharArray(), 0, text.length());
        Set<Chunk> sentences = chunking.chunkSet();
        ArrayList<String> sentenceList = new ArrayList<>();
        

        String slice = chunking.charSequence().toString();
        int i = 1;
        for (Iterator<Chunk> it = sentences.iterator(); it.hasNext();) {
            Chunk sentence = it.next();
            int start = sentence.start();
            int end = sentence.end();
            sentenceList.add(slice.substring(start, end));
           
        }
        return sentenceList;
    }
}
