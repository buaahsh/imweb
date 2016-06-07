
package com.cssrc.webservice.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cssrc.webservice.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDataAreas_QNAME = new QName("http://service.webservice.cssrc.com/", "getDataAreas");
    private final static QName _GetDataAreasResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getDataAreasResponse");
    private final static QName _GetDataPackMessage_QNAME = new QName("http://service.webservice.cssrc.com/", "getDataPackMessage");
    private final static QName _GetDataPackMessageResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getDataPackMessageResponse");
    private final static QName _GetNodeDependRelation_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeDependRelation");
    private final static QName _GetNodeDependRelationResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeDependRelationResponse");
    private final static QName _GetNodeDetail_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeDetail");
    private final static QName _GetNodeDetailResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeDetailResponse");
    private final static QName _GetNodeHistory_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeHistory");
    private final static QName _GetNodeHistoryResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getNodeHistoryResponse");
    private final static QName _GetSonnodes_QNAME = new QName("http://service.webservice.cssrc.com/", "getSonnodes");
    private final static QName _GetSonnodesResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getSonnodesResponse");
//    private final static QName _GetUserMessage_QNAME = new QName("http://service.webservice.cssrc.com/", "getUserMessage");
//    private final static QName _GetUserMessageResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "getUserMessageResponse");
    private final static QName _ImportMainModel_QNAME = new QName("http://service.webservice.cssrc.com/", "importMainModel");
    private final static QName _ImportMainModelResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "importMainModelResponse");
//    private final static QName _SubmitDataPack_QNAME = new QName("http://service.webservice.cssrc.com/", "submitDataPack");
//    private final static QName _SubmitDataPackResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "submitDataPackResponse");
    private final static QName _ValidateUser_QNAME = new QName("http://service.webservice.cssrc.com/", "validateUser");
    private final static QName _ValidateUserResponse_QNAME = new QName("http://service.webservice.cssrc.com/", "validateUserResponse");
    private final static QName _Exception_QNAME = new QName("http://service.webservice.cssrc.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cssrc.webservice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDataAreas }
     * 
     */
    public GetDataAreas createGetDataAreas() {
        return new GetDataAreas();
    }

    /**
     * Create an instance of {@link GetDataAreasResponse }
     * 
     */
    public GetDataAreasResponse createGetDataAreasResponse() {
        return new GetDataAreasResponse();
    }

    /**
     * Create an instance of {@link GetDataPackMessage }
     * 
     */
    public GetDataPackMessage createGetDataPackMessage() {
        return new GetDataPackMessage();
    }

    /**
     * Create an instance of {@link GetDataPackMessageResponse }
     * 
     */
    public GetDataPackMessageResponse createGetDataPackMessageResponse() {
        return new GetDataPackMessageResponse();
    }

    /**
     * Create an instance of {@link GetNodeDependRelation }
     * 
     */
    public GetNodeDependRelation createGetNodeDependRelation() {
        return new GetNodeDependRelation();
    }

    /**
     * Create an instance of {@link GetNodeDependRelationResponse }
     * 
     */
    public GetNodeDependRelationResponse createGetNodeDependRelationResponse() {
        return new GetNodeDependRelationResponse();
    }

    /**
     * Create an instance of {@link GetNodeDetail }
     * 
     */
    public GetNodeDetail createGetNodeDetail() {
        return new GetNodeDetail();
    }

    /**
     * Create an instance of {@link GetNodeDetailResponse }
     * 
     */
    public GetNodeDetailResponse createGetNodeDetailResponse() {
        return new GetNodeDetailResponse();
    }

    /**
     * Create an instance of {@link GetNodeHistory }
     * 
     */
    public GetNodeHistory createGetNodeHistory() {
        return new GetNodeHistory();
    }

    /**
     * Create an instance of {@link GetNodeHistoryResponse }
     * 
     */
    public GetNodeHistoryResponse createGetNodeHistoryResponse() {
        return new GetNodeHistoryResponse();
    }

    /**
     * Create an instance of {@link GetSonnodes }
     * 
     */
    public GetSonnodes createGetSonnodes() {
        return new GetSonnodes();
    }

    /**
     * Create an instance of {@link GetSonnodesResponse }
     * 
     */
    public GetSonnodesResponse createGetSonnodesResponse() {
        return new GetSonnodesResponse();
    }

    /**
     * Create an instance of {@link GetUserMessage }
     * 
     */
    public GetUserMessage createGetUserMessage() {
        return new GetUserMessage();
    }

    /**
     * Create an instance of {@link GetUserMessageResponse }
     * 
     */
    public GetUserMessageResponse createGetUserMessageResponse() {
        return new GetUserMessageResponse();
    }

    /**
     * Create an instance of {@link ImportMainModel }
     * 
     */
    public ImportMainModel createImportMainModel() {
        return new ImportMainModel();
    }

    /**
     * Create an instance of {@link ImportMainModelResponse }
     * 
     */
    public ImportMainModelResponse createImportMainModelResponse() {
        return new ImportMainModelResponse();
    }

    /**
     * Create an instance of {@link SubmitDataPack }
     * 
     */
//    public SubmitDataPack createSubmitDataPack() {
//        return new SubmitDataPack();
//    }

    /**
     * Create an instance of {@link SubmitDataPackResponse }
     * 
     */
    public SubmitDataPackResponse createSubmitDataPackResponse() {
        return new SubmitDataPackResponse();
    }

    /**
     * Create an instance of {@link ValidateUser }
     * 
     */
    public ValidateUser createValidateUser() {
        return new ValidateUser();
    }

    /**
     * Create an instance of {@link ValidateUserResponse }
     * 
     */
    public ValidateUserResponse createValidateUserResponse() {
        return new ValidateUserResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataAreas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getDataAreas")
    public JAXBElement<GetDataAreas> createGetDataAreas(GetDataAreas value) {
        return new JAXBElement<GetDataAreas>(_GetDataAreas_QNAME, GetDataAreas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataAreasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getDataAreasResponse")
    public JAXBElement<GetDataAreasResponse> createGetDataAreasResponse(GetDataAreasResponse value) {
        return new JAXBElement<GetDataAreasResponse>(_GetDataAreasResponse_QNAME, GetDataAreasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataPackMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getDataPackMessage")
    public JAXBElement<GetDataPackMessage> createGetDataPackMessage(GetDataPackMessage value) {
        return new JAXBElement<GetDataPackMessage>(_GetDataPackMessage_QNAME, GetDataPackMessage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataPackMessageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getDataPackMessageResponse")
    public JAXBElement<GetDataPackMessageResponse> createGetDataPackMessageResponse(GetDataPackMessageResponse value) {
        return new JAXBElement<GetDataPackMessageResponse>(_GetDataPackMessageResponse_QNAME, GetDataPackMessageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeDependRelation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeDependRelation")
    public JAXBElement<GetNodeDependRelation> createGetNodeDependRelation(GetNodeDependRelation value) {
        return new JAXBElement<GetNodeDependRelation>(_GetNodeDependRelation_QNAME, GetNodeDependRelation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeDependRelationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeDependRelationResponse")
    public JAXBElement<GetNodeDependRelationResponse> createGetNodeDependRelationResponse(GetNodeDependRelationResponse value) {
        return new JAXBElement<GetNodeDependRelationResponse>(_GetNodeDependRelationResponse_QNAME, GetNodeDependRelationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeDetail")
    public JAXBElement<GetNodeDetail> createGetNodeDetail(GetNodeDetail value) {
        return new JAXBElement<GetNodeDetail>(_GetNodeDetail_QNAME, GetNodeDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeDetailResponse")
    public JAXBElement<GetNodeDetailResponse> createGetNodeDetailResponse(GetNodeDetailResponse value) {
        return new JAXBElement<GetNodeDetailResponse>(_GetNodeDetailResponse_QNAME, GetNodeDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeHistory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeHistory")
    public JAXBElement<GetNodeHistory> createGetNodeHistory(GetNodeHistory value) {
        return new JAXBElement<GetNodeHistory>(_GetNodeHistory_QNAME, GetNodeHistory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeHistoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getNodeHistoryResponse")
    public JAXBElement<GetNodeHistoryResponse> createGetNodeHistoryResponse(GetNodeHistoryResponse value) {
        return new JAXBElement<GetNodeHistoryResponse>(_GetNodeHistoryResponse_QNAME, GetNodeHistoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSonnodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getSonnodes")
    public JAXBElement<GetSonnodes> createGetSonnodes(GetSonnodes value) {
        return new JAXBElement<GetSonnodes>(_GetSonnodes_QNAME, GetSonnodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSonnodesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getSonnodesResponse")
    public JAXBElement<GetSonnodesResponse> createGetSonnodesResponse(GetSonnodesResponse value) {
        return new JAXBElement<GetSonnodesResponse>(_GetSonnodesResponse_QNAME, GetSonnodesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserMessage }{@code >}}
     * 
     */
//    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getUserMessage")
//    public JAXBElement<GetUserMessage> createGetUserMessage(GetUserMessage value) {
//        return new JAXBElement<GetUserMessage>(_GetUserMessage_QNAME, GetUserMessage.class, null, value);
//    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserMessageResponse }{@code >}}
     * 
     */
//    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "getUserMessageResponse")
//    public JAXBElement<GetUserMessageResponse> createGetUserMessageResponse(GetUserMessageResponse value) {
//        return new JAXBElement<GetUserMessageResponse>(_GetUserMessageResponse_QNAME, GetUserMessageResponse.class, null, value);
//    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportMainModel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "importMainModel")
    public JAXBElement<ImportMainModel> createImportMainModel(ImportMainModel value) {
        return new JAXBElement<ImportMainModel>(_ImportMainModel_QNAME, ImportMainModel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportMainModelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "importMainModelResponse")
    public JAXBElement<ImportMainModelResponse> createImportMainModelResponse(ImportMainModelResponse value) {
        return new JAXBElement<ImportMainModelResponse>(_ImportMainModelResponse_QNAME, ImportMainModelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataPack }{@code >}}
     * 
     */
//    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "submitDataPack")
//    public JAXBElement<SubmitDataPack> createSubmitDataPack(SubmitDataPack value) {
//        return new JAXBElement<SubmitDataPack>(_SubmitDataPack_QNAME, SubmitDataPack.class, null, value);
//    }

//    /**
//     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitDataPackResponse }{@code >}}
//     * 
//     */
//    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "submitDataPackResponse")
//    public JAXBElement<SubmitDataPackResponse> createSubmitDataPackResponse(SubmitDataPackResponse value) {
//        return new JAXBElement<SubmitDataPackResponse>(_SubmitDataPackResponse_QNAME, SubmitDataPackResponse.class, null, value);
//    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "validateUser")
    public JAXBElement<ValidateUser> createValidateUser(ValidateUser value) {
        return new JAXBElement<ValidateUser>(_ValidateUser_QNAME, ValidateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "validateUserResponse")
    public JAXBElement<ValidateUserResponse> createValidateUserResponse(ValidateUserResponse value) {
        return new JAXBElement<ValidateUserResponse>(_ValidateUserResponse_QNAME, ValidateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.cssrc.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
