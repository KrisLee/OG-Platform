/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.web.analytics.blotter;

import static com.opengamma.web.analytics.blotter.BlotterColumn.MATURITY;
import static com.opengamma.web.analytics.blotter.BlotterColumn.PRODUCT;
import static com.opengamma.web.analytics.blotter.BlotterColumn.QUANTITY;
import static com.opengamma.web.analytics.blotter.BlotterColumn.RATE;
import static com.opengamma.web.analytics.blotter.BlotterColumn.TYPE;
import static org.testng.AssertJUnit.assertEquals;

import javax.time.calendar.TimeZone;
import javax.time.calendar.ZonedDateTime;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.opengamma.financial.currency.CurrencyPair;
import com.opengamma.financial.currency.CurrencyPairs;
import com.opengamma.financial.security.fra.FRASecurity;
import com.opengamma.financial.security.fx.FXForwardSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.master.security.ManageableSecurity;
import com.opengamma.util.money.Currency;

/**
 *
 */
public class BlotterColumnMapperTest {

  private static final CurrencyPairs s_currencyPairs = CurrencyPairs.of(ImmutableSet.of(CurrencyPair.of(Currency.GBP,
                                                                                                        Currency.USD)));
  private static final BlotterColumnMapper s_defaultMappings = DefaultBlotterColumnMappings.create(s_currencyPairs);

  /**
   * Simple security where fields are mapped using bean properties
   */
  @Test
  public void fra() {
    ExternalId regionId = ExternalId.of("Reg", "123");
    ExternalId underlyingId = ExternalId.of("Und", "321");
    ZonedDateTime startDate = ZonedDateTime.of(2012, 12, 21, 11, 0, 0, 0, TimeZone.UTC);
    ZonedDateTime endDate = ZonedDateTime.of(2013, 12, 21, 11, 0, 0, 0, TimeZone.UTC);
    ZonedDateTime fixingDate = ZonedDateTime.of(2013, 12, 20, 11, 0, 0, 0, TimeZone.UTC);
    FRASecurity security = new FRASecurity(Currency.AUD, regionId, startDate, endDate, 0.1, 1000, underlyingId, fixingDate);
    assertEquals("FRA", s_defaultMappings.valueFor(TYPE, security));
    assertEquals(Currency.AUD, s_defaultMappings.valueFor(PRODUCT, security));
    assertEquals(1000d, s_defaultMappings.valueFor(QUANTITY, security));
  }

  /**
   * Custom providers for values derived from multiple security properties
   */
  @Test
  public void fxForward() {
    ZonedDateTime forwardDate = ZonedDateTime.of(2012, 12, 21, 11, 0, 0, 0, TimeZone.UTC);
    ExternalId regionId = ExternalId.of("Reg", "123");
    FXForwardSecurity security = new FXForwardSecurity(Currency.USD, 150, Currency.GBP, 100, forwardDate, regionId);
    assertEquals("FX Forward", s_defaultMappings.valueFor(TYPE, security));
    assertEquals("GBP/USD", s_defaultMappings.valueFor(PRODUCT, security));
    assertEquals(forwardDate, s_defaultMappings.valueFor(MATURITY, security));
    assertEquals(FXAmounts.forForward(security, s_currencyPairs), s_defaultMappings.valueFor(QUANTITY, security));
    assertEquals(1.5d, s_defaultMappings.valueFor(RATE, security));
  }

  /**
   * if no columns are mapped for a class then it should inherit mappings set up for its superclasses
   */
  @Test
  public void inheritSuperclassMappings() {
    class A extends ManageableSecurity {}
    class B extends A {}
    class C extends B {}
    BlotterColumnMapper mapper = new BlotterColumnMapper();
    String aType = "A type";
    String bProduct = "B product";
    mapper.mapColumn(TYPE, A.class, aType);
    mapper.mapColumn(PRODUCT, B.class, bProduct);
    C c = new C();

    // check the case where there are no columns mapped for a subtype
    assertEquals(aType, mapper.valueFor(TYPE, c));
    assertEquals(bProduct, mapper.valueFor(PRODUCT, c));

    // add a mapping for the subtype and check the supertype mappings are still picked up
    String cMaturity = "C maturity";
    mapper.mapColumn(MATURITY, C.class, cMaturity);

    assertEquals(aType, mapper.valueFor(TYPE, c));
    assertEquals(bProduct, mapper.valueFor(PRODUCT, c));
    assertEquals(cMaturity, mapper.valueFor(MATURITY, c));

    // check overriding works
    String cType = "C type";
    mapper.mapColumn(TYPE, C.class, cType);
    assertEquals(cType, mapper.valueFor(TYPE, c));
  }
}