<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%> 
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<body id="body" >
 <div class="logoPlaceHolderLogin"><img src="images/tracer_logo.png"></div>
<div class="inactive-page">
    <div class="inactive-mesg">
     <logic:messagesPresent message="true">
            <ul id="messsages1">
              <html:messages id="msg" message="true">
                <li>
                  <bean:write name="msg" filter="false"/>
                </li>
              </html:messages>
            </ul>
          </logic:messagesPresent>
      <span class="inactive-icon"><i style="color:#00FF00;" class="fa fa-check"></i>
      </span><h3>Sent reset password details to your registered mail address successfully.</h3>
      <a href="/Tracer/forgotpassword.do?method=resentUserVerificationLink">Resend the email</a>
    </div>
</div>

  <div class="footer-main">
        <div class="footer">
          <p>TraceR<sup>&copy;</sup> SCI IT Solutions (P) Ltd. 2014</p>
        </div>
        <div class="footerterms">
          <a href="#" data-toggle="modal" data-target="#privatepolicy"> Privacy Policy</a> | 
          <a href="#" title="Copyright" data-toggle="modal" data-target="#myModal"> Copyright</a> |
          <a href="#" data-toggle="modal" data-target="#termsofuse"> Terms of use</a>
        </div>
      </div>
      </div>
      <!-- Modal For Copy Right-->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modelfooter">
          <div class="modal-content">
            <div class="modal-header footerdec">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">Copyright</h4>
            </div>
            <div class="modal-body footercontent">The material on this web site contains
              information that is subject to copyright protection or is otherwise
              proprietary to us. All rights reserved. The use of any information
              herein is strictly denied subject to formal written authorization
              from SCI IT Solutions Pvt. Ltd. Trademark Information All trademarks
              and registered trademarks appearing on the website are the property
              of their respective owners.
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Modal for Terms of Use -->
      <div class="modal fade" id="termsofuse" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modelfooter">
          <div class="modal-content">
            <div class="modal-header footerdec">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">Terms Of Use</h4>
            </div>
            <div class="modal-body footercontent" style="font-size: 14px;">
              <p>SCI Group and its affiliates (&ldquo;SCI Group&rdquo; or &ldquo;we&rdquo;) is committed to protecting the privacy of the personally-identifiable information that we collect from users of our Web sites (the &ldquo;Site&rdquo;). The scope of SCI Group&rsquo;s commitment is detailed in this Privacy Policy. By submitting information, you agree to SCI Group&rsquo;s use of such information as described herein. Please see our Terms of Use for more information about our on-line policies in general.</p>
              <h3>What personally-identifiable information is collected?</h3>
              <p>SCI Group collects personally-identifiable information on certain areas of the Site when users register, request publications or other information, sign up for conferences and events, apply for jobs, and participate in user posting areas, such as bulletin boards, discussion forums, and surveys. The personally-identifiable information collected may consist of information that you provide, such as names, mailing addresses, e-mail addresses, telephone, and, for recruiting purposes, any other personally-identifiable information on your resume. In addition, SCI Group collects certain information that you do not visibly enter, such as your IP address, browsing pattern on the Site, click stream, and the status of cookies placed on your computer by SCI Group, as described below.</p>
              <h3>How may SCI Group use my personally-identifiable information?</h3>
              <p>SCI Group uses your personally-identifiable information to fulfill your requests for information, to process your requests to participate in conferences and events, to evaluate any job applications or other employment-related inquiries that you may submit, or for such other business may initiate or request. We may keep any of your personally-identifiable information on file and use it to contact you for recruiting purposes. SCI Group also uses personally-identifiable information to perform statistical analyses of user behavior in order to measure interest in specific areas and information posted on our Site. SCI Group uses personally-identifiable information collected from cookies and IP addresses as described below.</p>
              <h3>Are cookies and IP addresses used?</h3>
              <p>The Site uses cookies to identify you and your interests and to track usage of the Site. Cookies are small pieces of text stored on your computer that help us know which browser you are using and where you have been on the Site and on Web sites to which you may link in order to use some of our features. By accepting our cookie, you will be permitted access to certain pages of the Site without having to log in each time you visit. A user who does not accept the cookie from the Site may not be able to access certain areas of the Site. SCI Group also logs IP addresses, or the location of computers on the Internet, to help diagnose problems with our server and to administer the Site. If you prefer not to accept a cookie, you can set your web browser to warn you before accepting cookies, or you can refuse all cookies by turning them off in your web browser.</p>
              <h3>Is personally-identifiable information disclosed to third parties?</h3>
              <p>SCI Group shall not intentionally disclose or transfer (and shall take reasonable steps to protect the confidentiality and security, and to prevent the unauthorized or accidental disclosure of) your personally-identifiable information to third parties (i.e., persons or entities that are not affiliates of SCI Group), whether for such third parties&rsquo; marketing purposes or otherwise, subject only to the following four exceptions: SCI Group may disclose your personally-identifiable information to third parties in the event that such disclosure is required by the laws, rules, or regulations of any nation, state, or other applicable jurisdiction; SCI Group may disclose your contact information to third parties as appropriate for SCI Group to make transmissions or deliveries to you; SCI Group may disclose your personally-identifiable information if, in connection with submitting the information, you consent to such disclosure; and SCI Group may disclose contact information for you in response to inquiries by bona-fide rights owners in connection with allegations of infringement of copyright or other proprietary rights arising from information you have posted on the Site or otherwise provided to SCI Group</p>
              <h3>Links to third-party sites</h3>
              <p>We may provide links to third-party Web sites as a service to our users. In addition, some of the content appearing to be on this Site is in fact supplied by third parties, for example, in instances of framing of third- party Web sites or incorporation through framesets of content supplied by third-party servers. Please be aware that we cannot control and are not responsible for the information collection practices of such third-party Web sites, which may differ from those of this Site. We encourage you to review and understand the privacy policies on these Web sites before providing any information to them.</p>
              <h3>How can I access, change, and/or delete information?</h3>
              <p>You may access, correct, update, and/or delete any personally-identifiable information that you submit to the Site. You may also unsubscribe from mailing lists or any registrations on the Site. To do so, please either follow instructions on the page of the Site on which you have provided such information or subscribed or registered or contact us directly.</p>
              <h3>Security</h3>
              <p>SCI Group has implemented generally accepted standards of technology and operational security in order to protect personally-identifiable information from loss, misuse, alteration, or destruction. Only authorized SCI Group personnel are provided access to personally-identifiable information, and these employees are required to treat this information as confidential. Despite these precautions, SCI Group cannot guarantee that unauthorized persons will not obtain access to your personally-identifiable information.</p>
              <h3>Transborder hosting and transfer of information</h3>
              <p>Personally-identifiable information collected on the Site may be transferred from time to time to SCI Group offices or personnel, or to third parties, located throughout the world, including offices located outside the European Economic Area (EEA), and the Site may be viewed and hosted anywhere in the world, including countries (such as the United States) that may not have laws of general applicability regulating the use and transfer of such data. By using the Site and submitting such information on it, you voluntarily consent to such transborder transfer and hosting of such information</p>
              <h3>Consent; Changes to Privacy Policy</h3>
              <p>By using the Site, you consent to the collection, use, and storage of your information by us in the manner described in this Privacy Policy and elsewhere on the Site. We reserve the right to make changes to this Privacy Policy from time to time. We will alert you to any such changes by updating this Privacy Policy. This Privacy Policy was last updated on May 15, 2009.</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Modal for Private Policy -->
      <div class="modal fade" id="privatepolicy" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modelfooter">
          <div class="modal-content">
            <div class="modal-header footerdec">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">Private Policy</h4>
            </div>
            <div class="modal-body footercontent" style="font-size: 14px;">
              <p>SCI Group and its affiliates (&ldquo;SCI Group&rdquo; or &ldquo;we&rdquo;) is committed to protecting the privacy of the personally-identifiable information that we collect from users of our Web sites (the &ldquo;Site&rdquo;). The scope of SCI Group&rsquo;s commitment is detailed in this Privacy Policy. By submitting information, you agree to SCI Group&rsquo;s use of such information as described herein. Please see our Terms of Use for more information about our on-line policies in general.</p>
              <h3>What personally-identifiable information is collected?</h3>
              <p>SCI Group collects personally-identifiable information on certain areas of the Site when users register, request publications or other information, sign up for conferences and events, apply for jobs, and participate in user posting areas, such as bulletin boards, discussion forums, and surveys. The personally-identifiable information collected may consist of information that you provide, such as names, mailing addresses, e-mail addresses, telephone, and, for recruiting purposes, any other personally-identifiable information on your resume. In addition, SCI Group collects certain information that you do not visibly enter, such as your IP address, browsing pattern on the Site, click stream, and the status of cookies placed on your computer by SCI Group, as described below.</p>
              <h3>How may SCI Group use my personally-identifiable information?</h3>
              <p>SCI Group uses your personally-identifiable information to fulfill your requests for information, to process your requests to participate in conferences and events, to evaluate any job applications or other employment-related inquiries that you may submit, or for such other business may initiate or request. We may keep any of your personally-identifiable information on file and use it to contact you for recruiting purposes. SCI Group also uses personally-identifiable information to perform statistical analyses of user behavior in order to measure interest in specific areas and information posted on our Site. SCI Group uses personally-identifiable information collected from cookies and IP addresses as described below.</p>
              <h3>Are cookies and IP addresses used?</h3>
              <p>The Site uses cookies to identify you and your interests and to track usage of the Site. Cookies are small pieces of text stored on your computer that help us know which browser you are using and where you have been on the Site and on Web sites to which you may link in order to use some of our features. By accepting our cookie, you will be permitted access to certain pages of the Site without having to log in each time you visit. A user who does not accept the cookie from the Site may not be able to access certain areas of the Site. SCI Group also logs IP addresses, or the location of computers on the Internet, to help diagnose problems with our server and to administer the Site. If you prefer not to accept a cookie, you can set your web browser to warn you before accepting cookies, or you can refuse all cookies by turning them off in your web browser.</p>
              <h3>Is personally-identifiable information disclosed to third parties?</h3>
              <p>SCI Group shall not intentionally disclose or transfer (and shall take reasonable steps to protect the confidentiality and security, and to prevent the unauthorized or accidental disclosure of) your personally-identifiable information to third parties (i.e., persons or entities that are not affiliates of SCI Group), whether for such third parties&rsquo; marketing purposes or otherwise, subject only to the following four exceptions: SCI Group may disclose your personally-identifiable information to third parties in the event that such disclosure is required by the laws, rules, or regulations of any nation, state, or other applicable jurisdiction; SCI Group may disclose your contact information to third parties as appropriate for SCI Group to make transmissions or deliveries to you; SCI Group may disclose your personally-identifiable information if, in connection with submitting the information, you consent to such disclosure; and SCI Group may disclose contact information for you in response to inquiries by bona-fide rights owners in connection with allegations of infringement of copyright or other proprietary rights arising from information you have posted on the Site or otherwise provided to SCI Group</p>
              <h3>Links to third-party sites</h3>
              <p>We may provide links to third-party Web sites as a service to our users. In addition, some of the content appearing to be on this Site is in fact supplied by third parties, for example, in instances of framing of third- party Web sites or incorporation through framesets of content supplied by third-party servers. Please be aware that we cannot control and are not responsible for the information collection practices of such third-party Web sites, which may differ from those of this Site. We encourage you to review and understand the privacy policies on these Web sites before providing any information to them.</p>
              <h3>How can I access, change, and/or delete information?</h3>
              <p>You may access, correct, update, and/or delete any personally-identifiable information that you submit to the Site. You may also unsubscribe from mailing lists or any registrations on the Site. To do so, please either follow instructions on the page of the Site on which you have provided such information or subscribed or registered or contact us directly.</p>
              <h3>Security</h3>
              <p>SCI Group has implemented generally accepted standards of technology and operational security in order to protect personally-identifiable information from loss, misuse, alteration, or destruction. Only authorized SCI Group personnel are provided access to personally-identifiable information, and these employees are required to treat this information as confidential. Despite these precautions, SCI Group cannot guarantee that unauthorized persons will not obtain access to your personally-identifiable information.</p>
              <h3>Transborder hosting and transfer of information</h3>
              <p>Personally-identifiable information collected on the Site may be transferred from time to time to SCI Group offices or personnel, or to third parties, located throughout the world, including offices located outside the European Economic Area (EEA), and the Site may be viewed and hosted anywhere in the world, including countries (such as the United States) that may not have laws of general applicability regulating the use and transfer of such data. By using the Site and submitting such information on it, you voluntarily consent to such transborder transfer and hosting of such information</p>
              <h3>Consent; Changes to Privacy Policy</h3>
              <p>By using the Site, you consent to the collection, use, and storage of your information by us in the manner described in this Privacy Policy and elsewhere on the Site. We reserve the right to make changes to this Privacy Policy from time to time. We will alert you to any such changes by updating this Privacy Policy. This Privacy Policy was last updated on May 15, 2009.</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
      </body>
      </html>
      