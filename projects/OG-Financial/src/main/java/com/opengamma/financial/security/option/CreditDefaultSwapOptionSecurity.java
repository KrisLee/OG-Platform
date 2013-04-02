/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.security.option;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.FinancialSecurityVisitor;
import com.opengamma.id.ExternalId;
import com.opengamma.util.money.Currency;

/**
 * 
 */
@BeanDefinition
public class CreditDefaultSwapOptionSecurity extends FinancialSecurity {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The security type.
   */
  public static final String SECURITY_TYPE = "CREDIT_DEFAULT_SWAP_OPTION";

  /**
   * Has the protection been bought. If false, protection has been sold.
   */
  @PropertyDefinition
  private boolean _isBuy;

  /**
   * The protection buyer.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _protectionBuyer;

  /**
   * The protection seller.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _protectionSeller;

  /**
   * The start date.
   */
  @PropertyDefinition(validate = "notNull")
  private ZonedDateTime _startDate;

  /**
   * The maturity date.
   */
  @PropertyDefinition(validate = "notNull")
  private ZonedDateTime _maturityDate;

  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _currency;

  /**
   * The notional.
   */
  @PropertyDefinition(validate = "notNull")
  private Double _notional;

  /**
   * The strike.
   */
  @PropertyDefinition(validate = "notNull")
  private Double _strike;

  /**
   * The knock-out type.
   */
  @PropertyDefinition
  private boolean _isKnockOut;

  /**
   * The option type, true if the option is a payer.
   */
  @PropertyDefinition(validate = "notNull")
  private boolean _isPayer;

  /**
   * The exercise type.
   */
  @PropertyDefinition(validate = "notNull")
  private ExerciseType _exerciseType;

  /**
   * The underlying CDS.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _underlyingId;

  /**
   * Constructor used by Fudge
   */
  CreditDefaultSwapOptionSecurity() {
    super(SECURITY_TYPE);
  }

  public CreditDefaultSwapOptionSecurity(final boolean buy, final ExternalId protectionBuyer, final ExternalId protectionSeller, final ZonedDateTime startDate,
      final ZonedDateTime maturityDate, final Currency currency, final Double notional, final Double strike,
      final boolean isKnockOut, final boolean payer, final ExerciseType exerciseType, final ExternalId underlyingId) {
    super(SECURITY_TYPE);
    setIsBuy(buy);
    setProtectionBuyer(protectionBuyer);
    setProtectionSeller(protectionSeller);
    setStartDate(startDate);
    setMaturityDate(maturityDate);
    setCurrency(currency);
    setNotional(notional);
    setStrike(strike);
    setIsKnockOut(isKnockOut);
    setIsPayer(payer);
    setExerciseType(exerciseType);
    setUnderlyingId(underlyingId);
  }

  @Override
  public <T> T accept(final FinancialSecurityVisitor<T> visitor) {
    return visitor.visitCreditDefaultSwapOptionSecurity(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CreditDefaultSwapOptionSecurity}.
   * @return the meta-bean, not null
   */
  public static CreditDefaultSwapOptionSecurity.Meta meta() {
    return CreditDefaultSwapOptionSecurity.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(CreditDefaultSwapOptionSecurity.Meta.INSTANCE);
  }

  @Override
  public CreditDefaultSwapOptionSecurity.Meta metaBean() {
    return CreditDefaultSwapOptionSecurity.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 100462844:  // isBuy
        return isIsBuy();
      case 2087835226:  // protectionBuyer
        return getProtectionBuyer();
      case 769920952:  // protectionSeller
        return getProtectionSeller();
      case -2129778896:  // startDate
        return getStartDate();
      case -414641441:  // maturityDate
        return getMaturityDate();
      case 575402001:  // currency
        return getCurrency();
      case 1585636160:  // notional
        return getNotional();
      case -891985998:  // strike
        return getStrike();
      case -1177436892:  // isKnockOut
        return isIsKnockOut();
      case 2067849291:  // isPayer
        return isIsPayer();
      case -466331342:  // exerciseType
        return getExerciseType();
      case -771625640:  // underlyingId
        return getUnderlyingId();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 100462844:  // isBuy
        setIsBuy((Boolean) newValue);
        return;
      case 2087835226:  // protectionBuyer
        setProtectionBuyer((ExternalId) newValue);
        return;
      case 769920952:  // protectionSeller
        setProtectionSeller((ExternalId) newValue);
        return;
      case -2129778896:  // startDate
        setStartDate((ZonedDateTime) newValue);
        return;
      case -414641441:  // maturityDate
        setMaturityDate((ZonedDateTime) newValue);
        return;
      case 575402001:  // currency
        setCurrency((Currency) newValue);
        return;
      case 1585636160:  // notional
        setNotional((Double) newValue);
        return;
      case -891985998:  // strike
        setStrike((Double) newValue);
        return;
      case -1177436892:  // isKnockOut
        setIsKnockOut((Boolean) newValue);
        return;
      case 2067849291:  // isPayer
        setIsPayer((Boolean) newValue);
        return;
      case -466331342:  // exerciseType
        setExerciseType((ExerciseType) newValue);
        return;
      case -771625640:  // underlyingId
        setUnderlyingId((ExternalId) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_protectionBuyer, "protectionBuyer");
    JodaBeanUtils.notNull(_protectionSeller, "protectionSeller");
    JodaBeanUtils.notNull(_startDate, "startDate");
    JodaBeanUtils.notNull(_maturityDate, "maturityDate");
    JodaBeanUtils.notNull(_currency, "currency");
    JodaBeanUtils.notNull(_notional, "notional");
    JodaBeanUtils.notNull(_strike, "strike");
    JodaBeanUtils.notNull(_isPayer, "isPayer");
    JodaBeanUtils.notNull(_exerciseType, "exerciseType");
    JodaBeanUtils.notNull(_underlyingId, "underlyingId");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CreditDefaultSwapOptionSecurity other = (CreditDefaultSwapOptionSecurity) obj;
      return JodaBeanUtils.equal(isIsBuy(), other.isIsBuy()) &&
          JodaBeanUtils.equal(getProtectionBuyer(), other.getProtectionBuyer()) &&
          JodaBeanUtils.equal(getProtectionSeller(), other.getProtectionSeller()) &&
          JodaBeanUtils.equal(getStartDate(), other.getStartDate()) &&
          JodaBeanUtils.equal(getMaturityDate(), other.getMaturityDate()) &&
          JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getNotional(), other.getNotional()) &&
          JodaBeanUtils.equal(getStrike(), other.getStrike()) &&
          JodaBeanUtils.equal(isIsKnockOut(), other.isIsKnockOut()) &&
          JodaBeanUtils.equal(isIsPayer(), other.isIsPayer()) &&
          JodaBeanUtils.equal(getExerciseType(), other.getExerciseType()) &&
          JodaBeanUtils.equal(getUnderlyingId(), other.getUnderlyingId()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsBuy());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProtectionBuyer());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProtectionSeller());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStartDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMaturityDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getNotional());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStrike());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsKnockOut());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsPayer());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExerciseType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUnderlyingId());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets has the protection been bought. If false, protection has been sold.
   * @return the value of the property
   */
  public boolean isIsBuy() {
    return _isBuy;
  }

  /**
   * Sets has the protection been bought. If false, protection has been sold.
   * @param isBuy  the new value of the property
   */
  public void setIsBuy(boolean isBuy) {
    this._isBuy = isBuy;
  }

  /**
   * Gets the the {@code isBuy} property.
   * @return the property, not null
   */
  public final Property<Boolean> isBuy() {
    return metaBean().isBuy().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the protection buyer.
   * @return the value of the property, not null
   */
  public ExternalId getProtectionBuyer() {
    return _protectionBuyer;
  }

  /**
   * Sets the protection buyer.
   * @param protectionBuyer  the new value of the property, not null
   */
  public void setProtectionBuyer(ExternalId protectionBuyer) {
    JodaBeanUtils.notNull(protectionBuyer, "protectionBuyer");
    this._protectionBuyer = protectionBuyer;
  }

  /**
   * Gets the the {@code protectionBuyer} property.
   * @return the property, not null
   */
  public final Property<ExternalId> protectionBuyer() {
    return metaBean().protectionBuyer().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the protection seller.
   * @return the value of the property, not null
   */
  public ExternalId getProtectionSeller() {
    return _protectionSeller;
  }

  /**
   * Sets the protection seller.
   * @param protectionSeller  the new value of the property, not null
   */
  public void setProtectionSeller(ExternalId protectionSeller) {
    JodaBeanUtils.notNull(protectionSeller, "protectionSeller");
    this._protectionSeller = protectionSeller;
  }

  /**
   * Gets the the {@code protectionSeller} property.
   * @return the property, not null
   */
  public final Property<ExternalId> protectionSeller() {
    return metaBean().protectionSeller().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start date.
   * @return the value of the property, not null
   */
  public ZonedDateTime getStartDate() {
    return _startDate;
  }

  /**
   * Sets the start date.
   * @param startDate  the new value of the property, not null
   */
  public void setStartDate(ZonedDateTime startDate) {
    JodaBeanUtils.notNull(startDate, "startDate");
    this._startDate = startDate;
  }

  /**
   * Gets the the {@code startDate} property.
   * @return the property, not null
   */
  public final Property<ZonedDateTime> startDate() {
    return metaBean().startDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the maturity date.
   * @return the value of the property, not null
   */
  public ZonedDateTime getMaturityDate() {
    return _maturityDate;
  }

  /**
   * Sets the maturity date.
   * @param maturityDate  the new value of the property, not null
   */
  public void setMaturityDate(ZonedDateTime maturityDate) {
    JodaBeanUtils.notNull(maturityDate, "maturityDate");
    this._maturityDate = maturityDate;
  }

  /**
   * Gets the the {@code maturityDate} property.
   * @return the property, not null
   */
  public final Property<ZonedDateTime> maturityDate() {
    return metaBean().maturityDate().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property, not null
   */
  public void setCurrency(Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<Currency> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional.
   * @return the value of the property, not null
   */
  public Double getNotional() {
    return _notional;
  }

  /**
   * Sets the notional.
   * @param notional  the new value of the property, not null
   */
  public void setNotional(Double notional) {
    JodaBeanUtils.notNull(notional, "notional");
    this._notional = notional;
  }

  /**
   * Gets the the {@code notional} property.
   * @return the property, not null
   */
  public final Property<Double> notional() {
    return metaBean().notional().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property, not null
   */
  public Double getStrike() {
    return _strike;
  }

  /**
   * Sets the strike.
   * @param strike  the new value of the property, not null
   */
  public void setStrike(Double strike) {
    JodaBeanUtils.notNull(strike, "strike");
    this._strike = strike;
  }

  /**
   * Gets the the {@code strike} property.
   * @return the property, not null
   */
  public final Property<Double> strike() {
    return metaBean().strike().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the knock-out type.
   * @return the value of the property
   */
  public boolean isIsKnockOut() {
    return _isKnockOut;
  }

  /**
   * Sets the knock-out type.
   * @param isKnockOut  the new value of the property
   */
  public void setIsKnockOut(boolean isKnockOut) {
    this._isKnockOut = isKnockOut;
  }

  /**
   * Gets the the {@code isKnockOut} property.
   * @return the property, not null
   */
  public final Property<Boolean> isKnockOut() {
    return metaBean().isKnockOut().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the option type, true if the option is a payer.
   * @return the value of the property, not null
   */
  public boolean isIsPayer() {
    return _isPayer;
  }

  /**
   * Sets the option type, true if the option is a payer.
   * @param isPayer  the new value of the property, not null
   */
  public void setIsPayer(boolean isPayer) {
    JodaBeanUtils.notNull(isPayer, "isPayer");
    this._isPayer = isPayer;
  }

  /**
   * Gets the the {@code isPayer} property.
   * @return the property, not null
   */
  public final Property<Boolean> isPayer() {
    return metaBean().isPayer().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exercise type.
   * @return the value of the property, not null
   */
  public ExerciseType getExerciseType() {
    return _exerciseType;
  }

  /**
   * Sets the exercise type.
   * @param exerciseType  the new value of the property, not null
   */
  public void setExerciseType(ExerciseType exerciseType) {
    JodaBeanUtils.notNull(exerciseType, "exerciseType");
    this._exerciseType = exerciseType;
  }

  /**
   * Gets the the {@code exerciseType} property.
   * @return the property, not null
   */
  public final Property<ExerciseType> exerciseType() {
    return metaBean().exerciseType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying CDS.
   * @return the value of the property, not null
   */
  public ExternalId getUnderlyingId() {
    return _underlyingId;
  }

  /**
   * Sets the underlying CDS.
   * @param underlyingId  the new value of the property, not null
   */
  public void setUnderlyingId(ExternalId underlyingId) {
    JodaBeanUtils.notNull(underlyingId, "underlyingId");
    this._underlyingId = underlyingId;
  }

  /**
   * Gets the the {@code underlyingId} property.
   * @return the property, not null
   */
  public final Property<ExternalId> underlyingId() {
    return metaBean().underlyingId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CreditDefaultSwapOptionSecurity}.
   */
  public static class Meta extends FinancialSecurity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code isBuy} property.
     */
    private final MetaProperty<Boolean> _isBuy = DirectMetaProperty.ofReadWrite(
        this, "isBuy", CreditDefaultSwapOptionSecurity.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code protectionBuyer} property.
     */
    private final MetaProperty<ExternalId> _protectionBuyer = DirectMetaProperty.ofReadWrite(
        this, "protectionBuyer", CreditDefaultSwapOptionSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code protectionSeller} property.
     */
    private final MetaProperty<ExternalId> _protectionSeller = DirectMetaProperty.ofReadWrite(
        this, "protectionSeller", CreditDefaultSwapOptionSecurity.class, ExternalId.class);
    /**
     * The meta-property for the {@code startDate} property.
     */
    private final MetaProperty<ZonedDateTime> _startDate = DirectMetaProperty.ofReadWrite(
        this, "startDate", CreditDefaultSwapOptionSecurity.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code maturityDate} property.
     */
    private final MetaProperty<ZonedDateTime> _maturityDate = DirectMetaProperty.ofReadWrite(
        this, "maturityDate", CreditDefaultSwapOptionSecurity.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", CreditDefaultSwapOptionSecurity.class, Currency.class);
    /**
     * The meta-property for the {@code notional} property.
     */
    private final MetaProperty<Double> _notional = DirectMetaProperty.ofReadWrite(
        this, "notional", CreditDefaultSwapOptionSecurity.class, Double.class);
    /**
     * The meta-property for the {@code strike} property.
     */
    private final MetaProperty<Double> _strike = DirectMetaProperty.ofReadWrite(
        this, "strike", CreditDefaultSwapOptionSecurity.class, Double.class);
    /**
     * The meta-property for the {@code isKnockOut} property.
     */
    private final MetaProperty<Boolean> _isKnockOut = DirectMetaProperty.ofReadWrite(
        this, "isKnockOut", CreditDefaultSwapOptionSecurity.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code isPayer} property.
     */
    private final MetaProperty<Boolean> _isPayer = DirectMetaProperty.ofReadWrite(
        this, "isPayer", CreditDefaultSwapOptionSecurity.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code exerciseType} property.
     */
    private final MetaProperty<ExerciseType> _exerciseType = DirectMetaProperty.ofReadWrite(
        this, "exerciseType", CreditDefaultSwapOptionSecurity.class, ExerciseType.class);
    /**
     * The meta-property for the {@code underlyingId} property.
     */
    private final MetaProperty<ExternalId> _underlyingId = DirectMetaProperty.ofReadWrite(
        this, "underlyingId", CreditDefaultSwapOptionSecurity.class, ExternalId.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "isBuy",
        "protectionBuyer",
        "protectionSeller",
        "startDate",
        "maturityDate",
        "currency",
        "notional",
        "strike",
        "isKnockOut",
        "isPayer",
        "exerciseType",
        "underlyingId");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100462844:  // isBuy
          return _isBuy;
        case 2087835226:  // protectionBuyer
          return _protectionBuyer;
        case 769920952:  // protectionSeller
          return _protectionSeller;
        case -2129778896:  // startDate
          return _startDate;
        case -414641441:  // maturityDate
          return _maturityDate;
        case 575402001:  // currency
          return _currency;
        case 1585636160:  // notional
          return _notional;
        case -891985998:  // strike
          return _strike;
        case -1177436892:  // isKnockOut
          return _isKnockOut;
        case 2067849291:  // isPayer
          return _isPayer;
        case -466331342:  // exerciseType
          return _exerciseType;
        case -771625640:  // underlyingId
          return _underlyingId;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CreditDefaultSwapOptionSecurity> builder() {
      return new DirectBeanBuilder<CreditDefaultSwapOptionSecurity>(new CreditDefaultSwapOptionSecurity());
    }

    @Override
    public Class<? extends CreditDefaultSwapOptionSecurity> beanType() {
      return CreditDefaultSwapOptionSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code isBuy} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isBuy() {
      return _isBuy;
    }

    /**
     * The meta-property for the {@code protectionBuyer} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> protectionBuyer() {
      return _protectionBuyer;
    }

    /**
     * The meta-property for the {@code protectionSeller} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> protectionSeller() {
      return _protectionSeller;
    }

    /**
     * The meta-property for the {@code startDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> startDate() {
      return _startDate;
    }

    /**
     * The meta-property for the {@code maturityDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> maturityDate() {
      return _maturityDate;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code notional} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> notional() {
      return _notional;
    }

    /**
     * The meta-property for the {@code strike} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> strike() {
      return _strike;
    }

    /**
     * The meta-property for the {@code isKnockOut} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isKnockOut() {
      return _isKnockOut;
    }

    /**
     * The meta-property for the {@code isPayer} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isPayer() {
      return _isPayer;
    }

    /**
     * The meta-property for the {@code exerciseType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExerciseType> exerciseType() {
      return _exerciseType;
    }

    /**
     * The meta-property for the {@code underlyingId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> underlyingId() {
      return _underlyingId;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
