/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package io.jing.base.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-04-19")
public class TokenBean implements org.apache.thrift.TBase<TokenBean, TokenBean._Fields>, java.io.Serializable, Cloneable, Comparable<TokenBean> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TokenBean");

  private static final org.apache.thrift.protocol.TField TRACE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("traceId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TICKET_FIELD_DESC = new org.apache.thrift.protocol.TField("ticket", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TokenBeanStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TokenBeanTupleSchemeFactory();

  public String traceId; // required
  public String userId; // required
  public String ticket; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRACE_ID((short)1, "traceId"),
    USER_ID((short)2, "userId"),
    TICKET((short)3, "ticket");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TRACE_ID
          return TRACE_ID;
        case 2: // USER_ID
          return USER_ID;
        case 3: // TICKET
          return TICKET;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRACE_ID, new org.apache.thrift.meta_data.FieldMetaData("traceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TICKET, new org.apache.thrift.meta_data.FieldMetaData("ticket", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TokenBean.class, metaDataMap);
  }

  public TokenBean() {
  }

  public TokenBean(
    String traceId,
    String userId,
    String ticket)
  {
    this();
    this.traceId = traceId;
    this.userId = userId;
    this.ticket = ticket;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TokenBean(TokenBean other) {
    if (other.isSetTraceId()) {
      this.traceId = other.traceId;
    }
    if (other.isSetUserId()) {
      this.userId = other.userId;
    }
    if (other.isSetTicket()) {
      this.ticket = other.ticket;
    }
  }

  public TokenBean deepCopy() {
    return new TokenBean(this);
  }

  @Override
  public void clear() {
    this.traceId = null;
    this.userId = null;
    this.ticket = null;
  }

  public String getTraceId() {
    return this.traceId;
  }

  public TokenBean setTraceId(String traceId) {
    this.traceId = traceId;
    return this;
  }

  public void unsetTraceId() {
    this.traceId = null;
  }

  /** Returns true if field traceId is set (has been assigned a value) and false otherwise */
  public boolean isSetTraceId() {
    return this.traceId != null;
  }

  public void setTraceIdIsSet(boolean value) {
    if (!value) {
      this.traceId = null;
    }
  }

  public String getUserId() {
    return this.userId;
  }

  public TokenBean setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public void unsetUserId() {
    this.userId = null;
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return this.userId != null;
  }

  public void setUserIdIsSet(boolean value) {
    if (!value) {
      this.userId = null;
    }
  }

  public String getTicket() {
    return this.ticket;
  }

  public TokenBean setTicket(String ticket) {
    this.ticket = ticket;
    return this;
  }

  public void unsetTicket() {
    this.ticket = null;
  }

  /** Returns true if field ticket is set (has been assigned a value) and false otherwise */
  public boolean isSetTicket() {
    return this.ticket != null;
  }

  public void setTicketIsSet(boolean value) {
    if (!value) {
      this.ticket = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRACE_ID:
      if (value == null) {
        unsetTraceId();
      } else {
        setTraceId((String)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((String)value);
      }
      break;

    case TICKET:
      if (value == null) {
        unsetTicket();
      } else {
        setTicket((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRACE_ID:
      return getTraceId();

    case USER_ID:
      return getUserId();

    case TICKET:
      return getTicket();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRACE_ID:
      return isSetTraceId();
    case USER_ID:
      return isSetUserId();
    case TICKET:
      return isSetTicket();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TokenBean)
      return this.equals((TokenBean)that);
    return false;
  }

  public boolean equals(TokenBean that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_traceId = true && this.isSetTraceId();
    boolean that_present_traceId = true && that.isSetTraceId();
    if (this_present_traceId || that_present_traceId) {
      if (!(this_present_traceId && that_present_traceId))
        return false;
      if (!this.traceId.equals(that.traceId))
        return false;
    }

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (!this.userId.equals(that.userId))
        return false;
    }

    boolean this_present_ticket = true && this.isSetTicket();
    boolean that_present_ticket = true && that.isSetTicket();
    if (this_present_ticket || that_present_ticket) {
      if (!(this_present_ticket && that_present_ticket))
        return false;
      if (!this.ticket.equals(that.ticket))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTraceId()) ? 131071 : 524287);
    if (isSetTraceId())
      hashCode = hashCode * 8191 + traceId.hashCode();

    hashCode = hashCode * 8191 + ((isSetUserId()) ? 131071 : 524287);
    if (isSetUserId())
      hashCode = hashCode * 8191 + userId.hashCode();

    hashCode = hashCode * 8191 + ((isSetTicket()) ? 131071 : 524287);
    if (isSetTicket())
      hashCode = hashCode * 8191 + ticket.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TokenBean other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTraceId()).compareTo(other.isSetTraceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTraceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.traceId, other.traceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTicket()).compareTo(other.isSetTicket());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTicket()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ticket, other.ticket);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TokenBean(");
    boolean first = true;

    sb.append("traceId:");
    if (this.traceId == null) {
      sb.append("null");
    } else {
      sb.append(this.traceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userId:");
    if (this.userId == null) {
      sb.append("null");
    } else {
      sb.append(this.userId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ticket:");
    if (this.ticket == null) {
      sb.append("null");
    } else {
      sb.append(this.ticket);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TokenBeanStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TokenBeanStandardScheme getScheme() {
      return new TokenBeanStandardScheme();
    }
  }

  private static class TokenBeanStandardScheme extends org.apache.thrift.scheme.StandardScheme<TokenBean> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TokenBean struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRACE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.traceId = iprot.readString();
              struct.setTraceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userId = iprot.readString();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TICKET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ticket = iprot.readString();
              struct.setTicketIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TokenBean struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.traceId != null) {
        oprot.writeFieldBegin(TRACE_ID_FIELD_DESC);
        oprot.writeString(struct.traceId);
        oprot.writeFieldEnd();
      }
      if (struct.userId != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.ticket != null) {
        oprot.writeFieldBegin(TICKET_FIELD_DESC);
        oprot.writeString(struct.ticket);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TokenBeanTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TokenBeanTupleScheme getScheme() {
      return new TokenBeanTupleScheme();
    }
  }

  private static class TokenBeanTupleScheme extends org.apache.thrift.scheme.TupleScheme<TokenBean> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TokenBean struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTraceId()) {
        optionals.set(0);
      }
      if (struct.isSetUserId()) {
        optionals.set(1);
      }
      if (struct.isSetTicket()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTraceId()) {
        oprot.writeString(struct.traceId);
      }
      if (struct.isSetUserId()) {
        oprot.writeString(struct.userId);
      }
      if (struct.isSetTicket()) {
        oprot.writeString(struct.ticket);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TokenBean struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.traceId = iprot.readString();
        struct.setTraceIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userId = iprot.readString();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.ticket = iprot.readString();
        struct.setTicketIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

