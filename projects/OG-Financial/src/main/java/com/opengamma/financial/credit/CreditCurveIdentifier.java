/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.credit;

import com.opengamma.id.ExternalId;
import com.opengamma.id.ObjectId;
import com.opengamma.id.ObjectIdentifiable;
import com.opengamma.id.UniqueId;
import com.opengamma.id.UniqueIdentifiable;
import com.opengamma.util.ArgumentChecker;

/**
 * Stores the date required to uniquely identify a credit curve - the issuer, seniority and restructuring clause
 */
public final class CreditCurveIdentifier implements UniqueIdentifiable, ObjectIdentifiable {
  /** The scheme to use in object identifiers */
  public static final String OBJECT_SCHEME = "CreditCurveIdentifier";
  private final ExternalId _issuer;
  private final String _seniority;
  private final String _restructuringClause;
  private final String _idValue;

  /**
   * Creates an {@code CreditCurveIdentifier} from issuer, seniority and restructuring clause data
   * @param issuer  the issuer, not null
   * @param seniority  the seniority, not null
   * @param restructuringClause  the restructuring clause, not null
   * @return the credit curve identifier, not null
   */
  public static CreditCurveIdentifier of(final ExternalId issuer, final String seniority, final String restructuringClause) {
    return new CreditCurveIdentifier(issuer, seniority, restructuringClause);
  }

  /**
   * Constructs a new instance
   * @param issuer  the issuer, not null
   * @param seniority  the seniority, not null
   * @param restructuringClause  the restructuring clause, not null
   */
  private CreditCurveIdentifier(final ExternalId issuer, final String seniority, final String restructuringClause) {
    ArgumentChecker.notNull(issuer, "issuer");
    ArgumentChecker.notNull(seniority, "seniority");
    ArgumentChecker.notNull(restructuringClause, "restructuring clause");
    _issuer = issuer;
    _seniority = seniority;
    _restructuringClause = restructuringClause;
    _idValue = _issuer.getValue() + "_" + _seniority + "_" + _restructuringClause;
  }

  /**
   * Gets the issuer.
   * @return the issuer
   */
  public ExternalId getIssuer() {
    return _issuer;
  }

  /**
   * Gets the seniority.
   * @return the seniority
   */
  public String getSeniority() {
    return _seniority;
  }

  /**
   * Gets the restructuring clause.
   * @return the restructuring clause
   */
  public String getRestructuringClause() {
    return _restructuringClause;
  }

  /**
   * Gets the object identifier.
   * <p>
   * This uses the scheme {@link #OBJECT_SCHEME CreditCurveIdentifier}.
   * @return the object identifier, not null
   */
  @Override
  public ObjectId getObjectId() {
    return ObjectId.of(OBJECT_SCHEME, _idValue);
  }

  /**
   * Gets the unique identifier.
   * <p>
   * The uses the scheme {@link #OBJECT_SCHEME CreditCurveIdentifier}
   * @return the unique identifier, not null
   */
  @Override
  public UniqueId getUniqueId() {
    return UniqueId.of(OBJECT_SCHEME, _idValue);
  }

  /**
   * Returns a suitable hash code for the identifier,
   * @return the hash code
   */
  @Override
  public int hashCode() {
    return _idValue.hashCode();
  }

  /**
   * Checks if this identifier equals another identifier.
   *
   * @param obj the other identifier, null returns false
   * @return true if equal
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof CreditCurveIdentifier) {
      return _idValue.equals(((CreditCurveIdentifier) obj)._idValue);
    }
    return false;
  }

  /**
   * Gets the credit curve identifier as a string
   *
   * @return the string representing this identifier, not null
   */
  @Override
  public String toString() {
    return _idValue;
  }
}
