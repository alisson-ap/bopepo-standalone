/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:12:06
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:12:06
 * 
 */


package org.jrimum.bopepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.bopepo.campolivre.CampoLivreFactory;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeCobranca;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeMoeda;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Teste Unitário para: <br />
 * {@link org.jrimum.bopepo.CodigoDeBarras#getFatorDeVencimento()}
 * 
 * 
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L.</a> 
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class TestCodigoDeBarrasFibra{

	private CampoLivre clFibra;

	private Titulo titulo;
	
	private CodigoDeBarras codigoDeBarras;
	
	private LinhaDigitavel linhaDigitavel;
	
	private Date VENCIMENTO = new GregorianCalendar(2025, Calendar.FEBRUARY, 22).getTime();

	@Before
	public void setUp() throws Exception {

		Sacado sacado = new Sacado("Sacado");
		Cedente cedente = new Cedente("Cedente", "08.816.304/0001-33");

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setBanco(BancosSuportados.BANCO_FIBRA.create());
		
		Agencia agencia = new Agencia(1234, "1");
		contaBancaria.setAgencia(agencia);
		
		Carteira carteira = new Carteira(101);
		carteira.setTipoCobranca(TipoDeCobranca.COM_REGISTRO);
		
		contaBancaria.setCarteira(carteira);
		
		NumeroDaConta numeroDaConta = new NumeroDaConta();
		numeroDaConta.setCodigoDaConta(6789);
		contaBancaria.setNumeroDaConta(numeroDaConta);

		titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNossoNumero("1234567890");
		titulo.setDigitoDoNossoNumero("1");
		titulo.setTipoDeMoeda(TipoDeMoeda.REAL);
		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoVencimento(VENCIMENTO);
		titulo.setAceite(Aceite.N);
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		
		clFibra = CampoLivreFactory.create(titulo);
		
		codigoDeBarras = new CodigoDeBarras(titulo, clFibra);
		
		linhaDigitavel = new LinhaDigitavel (codigoDeBarras);
	}

	/**
	 * Test method for
	 * {@link org.jrimum.bopepo.CodigoDeBarras#getDigitoVerificadorGeral()}.
	 */
	@Test
	public void testGetDigitoVerificadorGeral() {
		assertTrue(1 == codigoDeBarras.getDigitoVerificadorGeral().getValue());
	}

	/**
	 * Test method for
	 * {@link org.jrimum.bopepo.CodigoDeBarras#toString()}.
	 */
	@Test
	public void testWrite() {
		
		assertEquals("22491100000000100231234101000678912345678901", codigoDeBarras.write());
		
	}

	/**
	 * Test method for
	 * {@link org.jrimum.bopepo.LinhaDigitavel#toString()}.
	 */
	@Test
	public void testLinhaDigitavel() {
		
		assertEquals("22491.23411 01000.678910 23456.789017 1 10000000010023", linhaDigitavel.write());
		
	}

	/**
	 * Test method for
	 * {@link org.jrimum.bopepo.CodigoDeBarras#getFatorDeVencimento()}.
	 */
	@Test
	public void testGetFatorDeVencimento() {
		
		assertTrue(1000 == codigoDeBarras.getFatorDeVencimento().getValue());
		
	}

}
