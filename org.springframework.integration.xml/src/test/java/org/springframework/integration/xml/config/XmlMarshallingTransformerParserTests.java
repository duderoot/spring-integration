/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.xml.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.xml.transform.dom.DOMResult;

import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Document;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.handler.MessageHandler;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.message.Message;
import org.springframework.xml.transform.StringResult;

/**
 * @author Jonas Partner
 */
public class XmlMarshallingTransformerParserTests  {

	private ApplicationContext appContext;


	@Before
	public void setUp() {
		this.appContext = new ClassPathXmlApplicationContext("XmlMarshallingTransformerParserTests-context.xml", getClass());
	}


	@Test
	public void testDefault() throws Exception {
		MessageHandler transformer = (MessageHandler) appContext.getBean("marshallingTransfomerNoResultFactory");
		GenericMessage<Object> message = new GenericMessage<Object>("hello");
		Message<?> result = transformer.handle(message);
		assertTrue("Wrong payload type", result.getPayload() instanceof DOMResult);
		Document doc = (Document) ((DOMResult) result.getPayload()).getNode();
		assertEquals("Wrong palyoad", "hello", doc.getDocumentElement().getTextContent());
	}

	@Test
	public void testDOMResult() throws Exception {
		MessageHandler transformer = (MessageHandler) appContext.getBean("marshallingTransfomerDOMResultFactory");
		GenericMessage<Object> message = new GenericMessage<Object>("hello");
		Message<?> result = transformer.handle(message);
		assertTrue("Wrong payload type ", result.getPayload() instanceof DOMResult);
		Document doc = (Document) ((DOMResult) result.getPayload()).getNode();
		assertEquals("Wrong palyoad", "hello", doc.getDocumentElement().getTextContent());
	}

	@Test
	public void testStringResult() throws Exception {
		MessageHandler transformer = (MessageHandler) appContext.getBean("marshallingTransfomerStringResultFactory");
		GenericMessage<Object> message = new GenericMessage<Object>("hello");
		Message<?> result = transformer.handle(message);
		assertTrue("Wrong payload type", result.getPayload() instanceof StringResult);
	}

}
