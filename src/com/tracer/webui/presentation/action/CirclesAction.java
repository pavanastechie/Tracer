/**
 * @author Mohini
 **/
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.ACTIVE;
import static com.tracer.common.Constants.IN_ACTIVE;
import static com.tracer.common.Constants.ADD_CIRCLE;
import static com.tracer.common.Constants.ADD_CIRCLE_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.CIRCLES_MANAGER;
import static com.tracer.common.Constants.CIRCLE_CODE;
import static com.tracer.common.Constants.CIRCLE_DETAILS;
import static com.tracer.common.Constants.CIRCLE_ERROR_MESSAGE;
import static com.tracer.common.Constants.CIRCLE_ID;
import static com.tracer.common.Constants.CIRCLE_NAME;
import static com.tracer.common.Constants.EDIT;
import static com.tracer.common.Constants.ERROR_MESSAGE;
import static com.tracer.common.Constants.HUBS;
import static com.tracer.common.Constants.MANAGE_CIRCLES_LIST;
import static com.tracer.common.Constants.REGIONS;
import static com.tracer.common.Constants.RESULT;
import static com.tracer.common.Constants.ROLE_ID;
import static com.tracer.common.Constants.SUCCESS;
import static com.tracer.common.Constants.SUCCESS_MESSAGE;
import static com.tracer.common.Constants.USER_ID;
import static com.tracer.common.Constants.VIEW_TYPE;
import static com.tracer.common.Constants.ZONES;
import static com.tracer.common.Constants.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.json.JSONObject;

import com.tracer.common.Constants;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.service.CirclesManager;
import com.tracer.util.StringUtil;
import com.tracer.util.UniqueCodeGenerator;
import com.tracer.webui.presentation.form.CirclesForm;

public class CirclesAction extends BaseDispatchAction {

	protected transient final Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * showAddCirclesPage method is used to generate the circle code from
	 * uniqueCodGenerator class and put the circle code in session for automatic
	 * pouplation in the add circles page
	 */

	@SuppressWarnings({ "rawtypes", "unused" })
	public ActionForward showAddCirclePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward showAddCirclePage");
		CircleDetails circleDetails = null;
		HttpSession session = null;
		Map circlesMap = null;
		CirclesForm circlesForm = null;
		try {
		} catch (Exception e) {
			log.error("Problem in the ActionForward showAddCirclePage");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward showAddCirclePage");
		return mapping.findForward(Constants.ADD_CIRCLE);
	}

	// ========================================================================

	public ActionForward showNewAddCirclePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward showNewAddCirclePage");
		HttpSession session = null;
		CirclesForm circlesForm = null;
		try {
			circlesForm = (CirclesForm) form;
			BeanUtils.copyProperties(circlesForm, new CirclesForm());
			session = request.getSession(false);
			if (session != null) {
				// if(session.getAttribute(VIEW_TYPE) != null &&
				// session.getAttribute(VIEW_TYPE).equals(EDIT)) {
				session.removeAttribute(CIRCLE_DETAILS);
				session.removeAttribute(CIRCLE_NAME);
				session.removeAttribute(CIRCLE_CODE);
				session.removeAttribute(REGIONS);
				session.removeAttribute(ZONES);
				session.removeAttribute(HUBS);
				session.removeAttribute("circlesMap");
				session.removeAttribute(VIEW_TYPE);
				// }
			}
		} catch (Exception e) {
			log.error("Problem in the ActionForward showNewAddCirclePage");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward showNewAddCirclePage");
		return mapping.findForward(Constants.ADD_CIRCLE);
	}

	// =========================================================================

	/**
	 * 
	 * addCircles method is used to add the circles(circle id and circle name).
	 * This page is also used for editing the circle after that add the circle
	 * details in the circlesmap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward addCircle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward addCircle");

		CirclesForm circlesForm = null;
		String circleName = null;
		String circleCode = null;
		Long circleId = 0L;
		CirclesManager circlesManager = null;

		HttpSession session = null;
		CircleDetails circleDetails = null;
		Map circlesMap = null;

		session = request.getSession();
		circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
		circlesForm = (CirclesForm) form;

		String forwardPage = ADD_CIRCLE;

		try {
			// if (circlesManager.isCircleNameExists(circlesForm.getCircleName())) {
			// log.info("Circle name already exist !!");
			// request.setAttribute(CIRCLE_ERROR_MESSAGE,
			// "Circle name already exist !!");
			// } else {
			if (session.getAttribute("circlesMap") == null) {
				circlesMap = new HashMap();
			} else {
				circlesMap = (Map) session.getAttribute("circlesMap");
				circleId = (Long) session.getAttribute(CIRCLE_ID);
			}
			if (circleId != null && circleId > 0) {
				circlesMap = (Map) circlesManager.getCircleDetails(circleId);
				circleDetails = (CircleDetails) circlesMap.get(CIRCLE_DETAILS);
			} else {
				circleDetails = new CircleDetails();
			}
			circleName = circlesForm.getCircleName();
			circleCode = circlesForm.getCircleCode();
			session.setAttribute(CIRCLE_NAME, circleName);
			session.setAttribute(CIRCLE_CODE, circleCode);
			circleDetails.setCircleName(circleName);
			circleDetails.setCircleCode(circleCode);
			circleDetails.setStatus(ACTIVE);
			circleDetails.setDateTime(new Date());
			UserDetails userDetails = new UserDetails();
			userDetails.setUserId((Long) session.getAttribute(USER_ID));
			circleDetails.setUserDetails(userDetails);
			circlesMap.put(CIRCLE_DETAILS, circleDetails);
			session.setAttribute("circlesMap", circlesMap);
			// }
		} catch (Exception e) {
			log.error("Problem in the ActionForward addCircle");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward addCircle");
		return mapping.findForward(forwardPage);
	}

	// ========================================================================

	public ActionForward updateCircle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward updateCircle");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward updateCircle");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward updateCircle");
		return mapping.findForward(Constants.UPDATE_CIRCLE);
	}

	// ========================================================================

	public ActionForward deleteCircle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward deleteCircle");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward deleteCircle");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward deleteCircle");
		return mapping.findForward(Constants.DELETE_CIRCLE);
	}

	// ========================================================================

	public ActionForward displayCircle(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward displayCircle");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward displayCircle");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward displayCircle");
		return mapping.findForward(Constants.DISPLAY_CIRCLE);
	}

	// ========================================================================

	public ActionForward listCircles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward listCircles");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward listCircles");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward listCircles");
		return mapping.findForward(Constants.LIST_CIRCLES);
	}

	// ========================================================================

	/**
	 * addRegion method is used for adding the regions which are added in the
	 * popup in the addcircles.jsp page and all those added values are put in the
	 * json and parse this json in this method.Add the regionslist to circlesmap
	 * 
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward addRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward addRegion");
		HttpSession session = null;
		List regionsList = null;
		CircleDetails circleDetails = null;
		Map circlesMap = null;
		RegionDetails regionDetails = null;

		try {
			String jsonResponse = request.getParameter("regionsList");
			session = request.getSession(false);
			if (session.getAttribute("circlesMap") != null) {
				circlesMap = (Map) session.getAttribute("circlesMap");
				if (circlesMap != null && circlesMap.get(CIRCLE_DETAILS) != null) {
					circleDetails = (CircleDetails) circlesMap.get(CIRCLE_DETAILS);
					if (circleDetails != null) {

						// if (session.getAttribute(REGIONS) != null) {
						// regionsList = (List) session.getAttribute(REGIONS);
						// } else {
						regionsList = new ArrayList();
						// }
						JSONArray jsonArray = JSONArray.fromObject(jsonResponse);
						Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
						for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
							Map<String, String> mapObject = (Map<String, String>) iterator.next();
							regionDetails = new RegionDetails();
							UserDetails userDetails = new UserDetails();
							userDetails.setUserId((Long) session.getAttribute(USER_ID));
							regionDetails.setUserDetails(userDetails);
							regionDetails.setRegionId(Long.parseLong(mapObject.get("regionId")));
							regionDetails.setRegionCode(mapObject.get("regionCode"));
							regionDetails.setRegionName(mapObject.get("regionName"));
							circleDetails = (CircleDetails) circlesMap.get(CIRCLE_DETAILS);
							regionDetails.setCircleDetails(circleDetails);
							regionDetails.setDateTime(new Date());
							if (request.getParameter(STATUS) != null && request.getParameter(STATUS).equals(IN_ACTIVE))
								regionDetails.setStatus(IN_ACTIVE);
							else
								regionDetails.setStatus(ACTIVE);
							regionsList.add(regionDetails);
						}
						session.setAttribute("regionsList", regionsList);
						circlesMap.put(REGIONS, regionsList);

					} else {
						log.info("circle details are null");
					}
				} else {
					log.info("there is no circle key in the map");
				}

			} else {
				log.info("there is no map in the session");
			}
		} catch (Exception e) {
			log.error("Problem in the ActionForward addRegion");
			e.printStackTrace();
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward addRegion");
		return mapping.findForward(Constants.ADD_CIRCLE);
	}

	// ========================================================================

	public ActionForward updateRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward updateRegion");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward updateRegion");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward updateRegion");
		return mapping.findForward(Constants.UPDATE_REGION);
	}

	// ========================================================================

	public ActionForward deleteRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward deleteRegion");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward deleteRegion");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward deleteRegion");
		return mapping.findForward(Constants.DELETE_REGION);
	}

	// ========================================================================

	public ActionForward displayRegion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward displayRegion");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward displayRegion");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward displayRegion");
		return mapping.findForward(Constants.DISPLAY_REGION);
	}

	// ========================================================================

	public ActionForward listRegions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward listRegions");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward listRegions");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward listRegions");
		return mapping.findForward(Constants.LIST_REGIONS);
	}

	// ========================================================================
	/**
	 * addZone method is used for adding the zones which are added in the popup in
	 * the addcircles.jsp page and all those added values are put in the json and
	 * parse this json in this method and added zoneslist to circlesmap and put
	 * the zoneslist in the session
	 * 
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward addZone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward addZone");
		HttpSession session = null;
		List zonesList = null;
		RegionDetails regionDetails = null;
		Map circlesMap = null;
		ZoneDetails zoneDetails = null;
		List regionDetailsList = null;

		try {
			String jsonResponse = request.getParameter("zonesList");
			circlesMap = new HashMap();
			session = request.getSession(false);
			if (session.getAttribute("circlesMap") != null) {
				circlesMap = (Map) session.getAttribute("circlesMap");
				if (circlesMap != null && circlesMap.get(CIRCLE_DETAILS) != null && circlesMap.get(REGIONS) != null) {
					regionDetailsList = (List) circlesMap.get(REGIONS);
					if (regionDetailsList != null && regionDetailsList.size() > 0) {
						// if (session.getAttribute(ZONES) != null) {
						// zonesList = (List) session.getAttribute(ZONES);
						//
						// } else {
						zonesList = new ArrayList();
						// }
						JSONArray jsonArray = JSONArray.fromObject(jsonResponse);

						Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
						for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
							Map<String, String> mapObject = (Map<String, String>) iterator.next();
							zoneDetails = new ZoneDetails();
							UserDetails userDetails = new UserDetails();
							userDetails.setUserId((Long) session.getAttribute(USER_ID));
							zoneDetails.setUserDetails(userDetails);
							zoneDetails.setZoneId(Long.parseLong(mapObject.get("zoneId")));
							zoneDetails.setZoneCode(mapObject.get("zoneCode"));
							zoneDetails.setZoneName(mapObject.get("zoneName"));
							regionDetails = new RegionDetails();
							regionDetails.setRegionCode(mapObject.get("regionCode"));
							regionDetails.setRegionName(mapObject.get("regionName"));
							zoneDetails.setDateTime(new Date());
							if (request.getParameter(STATUS) != null && request.getParameter(STATUS).equals(IN_ACTIVE))
								zoneDetails.setStatus(IN_ACTIVE);
							else
								zoneDetails.setStatus(ACTIVE);
							zoneDetails.setRegionDetails(regionDetails);
							zonesList.add(zoneDetails);

						}
						log.info("zoneslist is--" + zonesList);
						session.setAttribute(ZONES, zonesList);
						circlesMap.put(ZONES, zonesList);
					} else {
						log.info("regiondetailslist is null or 0");
					}

				} else {
					log.info("either of the cirlcesmap of regionslist or circle is null");
				}
			} else {
				log.info("circles map is null");
			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward addZone");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward addZone");
		return mapping.findForward(Constants.ADD_ZONE);
	}

	// ========================================================================

	public ActionForward updateZone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward updateZone");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward updateZone");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward updateZone");
		return mapping.findForward(Constants.UPDATE_ZONE);
	}

	// ========================================================================

	public ActionForward deleteZone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward deleteZone");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward deleteZone");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward deleteZone");
		return mapping.findForward(Constants.DELETE_ZONE);
	}

	// ========================================================================

	public ActionForward displayZone(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward displayZone");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward displayZone");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward displayZone");
		return mapping.findForward(Constants.DISPLAY_ZONE);
	}

	// ========================================================================

	public ActionForward listZones(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward listZones");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward listZones");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward listZones");
		return mapping.findForward(Constants.LIST_ZONES);
	}

	// ========================================================================

	/**
	 * addHub method is used for adding the hubs which are added in the popup in
	 * the addcircles.jsp page and all those added values are put in the json and
	 * parse this json in this method and add this hubslist to circlesmap and put
	 * the hubslist in the session
	 * 
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward addHub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward addHub");
		HttpSession session = null;
		List hubsList = null;
		HubDetails hubDetails = null;
		ZoneDetails zoneDetails = null;
		Map circlesMap = null;
		List zoneDetailsList = null;

		try {
			String jsonResponse = request.getParameter("hubsList");
			circlesMap = new HashMap();
			session = request.getSession(false);
			if (session.getAttribute("circlesMap") != null) {
				circlesMap = (Map) session.getAttribute("circlesMap");
				if (circlesMap != null && circlesMap.get(CIRCLE_DETAILS) != null && circlesMap.get(REGIONS) != null && circlesMap.get(ZONES) != null) {
					zoneDetailsList = (List) circlesMap.get(ZONES);
					if (zoneDetailsList != null && zoneDetailsList.size() > 0) {
						// if (session.getAttribute(HUBS) != null) {
						// hubsList = (List) session.getAttribute(HUBS);
						//
						// } else {
						hubsList = new ArrayList();
						// }

						JSONArray jsonArray = JSONArray.fromObject(jsonResponse);
						Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
						for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
							Map<String, String> mapObject = (Map<String, String>) iterator.next();
							hubDetails = new HubDetails();
							zoneDetails = new ZoneDetails();
							UserDetails userDetails = new UserDetails();
							userDetails.setUserId((Long) session.getAttribute(USER_ID));
							hubDetails.setUserDetails(userDetails);
							hubDetails.setHubId(Long.parseLong(mapObject.get("hubId")));
							hubDetails.setHubCode(mapObject.get("hubCode"));
							hubDetails.setHubName(mapObject.get("hubName"));
							zoneDetails.setZoneCode(mapObject.get("zoneCode"));
							zoneDetails.setZoneName(mapObject.get("zoneName"));
							if (request.getParameter(STATUS) != null && request.getParameter(STATUS).equals(IN_ACTIVE))
								hubDetails.setStatus(IN_ACTIVE);
							else
								hubDetails.setStatus(ACTIVE);
							hubDetails.setDateTime(new Date());
							hubDetails.setZoneDetails(zoneDetails);
							hubsList.add(hubDetails);
						}
						session.setAttribute(HUBS, hubsList);
						circlesMap.put(HUBS, hubsList);

					} else {
						log.info("regiondetailslist is null or 0");
					}

				} else {
					log.info("either of the cirlcesmap of regionslist or circle is null");
				}
			} else {
				log.info("circles map is null");
			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward addHub");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward addHub");
		return mapping.findForward(Constants.ADD_HUB);
	}

	// ========================================================================

	public ActionForward updateHub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward updateHub");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward updateHub");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward updateHub");
		return mapping.findForward(Constants.UPDATE_HUB);
	}

	// ========================================================================

	public ActionForward deleteHub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward deleteHub");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward deleteHub");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward deleteHub");
		return mapping.findForward(Constants.DELETE_HUB);
	}

	// ========================================================================

	public ActionForward displayHub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward displayHub");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward displayHub");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward displayHub");
		return mapping.findForward(Constants.DISPLAY_HUB);
	}

	// ========================================================================

	public ActionForward listHubs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward listHubs");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward listHubs");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward listHubs");
		return mapping.findForward(Constants.LIST_HUBS);
	}

	/**
	 * 
	 * saveCircles method is used for adding the circles and updating the circles
	 * based on Add and Update conditions. this method get the details of
	 * circles,regions,zones,hubs and call the dao layer for saving to db
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward saveCircles(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward saveCircles");
		Map circlesMap = null;
		List<RegionDetails> regionsList = null;
		List<ZoneDetails> zonesList = null;
		List<HubDetails> hubsList = null;
		Map resultMap = null;
		CirclesManager circlesManager = null;
		String forwardPage = MANAGE_CIRCLES_LIST;
		String viewType = null;
		HttpSession session = null;
		String zoneErrorMsg = null;
		String regionErrorMsg = null;

		try {
			circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
			session = request.getSession(false);
			if (session != null) {
				viewType = (String) session.getAttribute(VIEW_TYPE);
				if (session.getAttribute("circlesMap") != null) {
					circlesMap = (Map) session.getAttribute("circlesMap");

					if (circlesMap.get(CIRCLE_DETAILS) != null) {

						if (circlesMap.get(REGIONS) != null) {
							regionsList = (List) circlesMap.get(REGIONS);
							if (regionsList.size() > 0) {
								for (int i = 0; i < regionsList.size(); i++) {
									if (regionsList.get(i).getRegionId() != null && regionsList.get(i).getRegionId() == 0) {
										regionsList.get(i).setRegionId(null);
									}
								}
								if (circlesMap.get(ZONES) != null) {
									zonesList = (List) circlesMap.get(ZONES);
									if (zonesList.size() > 0) {
										for (int i = 0; i < zonesList.size(); i++) {
											if (zonesList.get(i).getZoneId() != null && zonesList.get(i).getZoneId() == 0) {
												zonesList.get(i).setZoneId(null);
											}
										}
										if (circlesMap.get(HUBS) != null) {

											hubsList = (List) circlesMap.get(HUBS);
											if (hubsList.size() > 0) {
												for (int i = 0; i < hubsList.size(); i++) {
													if (hubsList.get(i).getHubId() != null && hubsList.get(i).getHubId() == 0) {
														hubsList.get(i).setHubId(null);
													}
												}

												if (regionsList != null && zonesList != null && hubsList != null && regionsList.size() > 0 && zonesList.size() > 0
														&& hubsList.size() > 0) {
													for (RegionDetails regionDetails : regionsList) {
														if (regionDetails != null) {
															List<ZoneDetails> zonesOfRegion = getZonesOfRegion(zonesList, regionDetails.getRegionCode());

															if (zonesOfRegion != null && zonesOfRegion.size() > 0) {
																for (ZoneDetails zoneDetails : zonesOfRegion) {
																	zoneDetails.setRegionDetails(regionDetails);
																	List<HubDetails> hubsOfZone = getHubsOfZone(hubsList, zoneDetails.getZoneCode());

																	if (hubsOfZone != null && hubsOfZone.size() > 0) {
																		for (HubDetails hubDetails : hubsOfZone) {
																			hubDetails.setZoneDetails(zoneDetails);
																		}
																	} else {
																		zoneErrorMsg = "Every Zone must have atleast one Hub";
																	}
																}
															} else {
																regionErrorMsg = "Every Region must have atleast one Zone";
															}
														}
													}
												} else {
												}

												if (zoneErrorMsg == null && regionErrorMsg == null) {
													if (viewType != null && viewType.equals(EDIT)) {
														resultMap = circlesManager.updateCircle(circlesMap);
														if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
															log.info("Edit circle success");
															request.setAttribute(SUCCESS_MESSAGE, EDIT_CIRCLE_SUCCESS_MESSAGE);
															forwardPage = MANAGE_CIRCLES_LIST;
															session.removeAttribute(CIRCLE_DETAILS);
															session.removeAttribute(CIRCLE_NAME);
															session.removeAttribute(CIRCLE_CODE);
															session.removeAttribute(REGIONS);
															session.removeAttribute(ZONES);
															session.removeAttribute(HUBS);
															session.removeAttribute("circlesMap");
															session.removeAttribute(VIEW_TYPE);
														} else if (resultMap.get(ERROR_MESSAGE) != null) {
															request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
															forwardPage = ADD_CIRCLE;
														}
													} else {
														resultMap = circlesManager.addCircle(circlesMap);
														if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
															log.info("Add Circles success");
															request.setAttribute(SUCCESS_MESSAGE, ADD_CIRCLE_SUCCESS_MESSAGE);
															// request.setAttribute(SUCCESS_MESSAGE,
															// "circle editted successfuly");
															forwardPage = MANAGE_CIRCLES_LIST;
															session.removeAttribute(CIRCLE_DETAILS);
															session.removeAttribute(CIRCLE_NAME);
															session.removeAttribute(CIRCLE_CODE);
															session.removeAttribute(REGIONS);
															session.removeAttribute(ZONES);
															session.removeAttribute(HUBS);
															session.removeAttribute("circlesMap");
														} else if (resultMap.get(ERROR_MESSAGE) != null) {
															request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
															forwardPage = ADD_CIRCLE;
														}
													}
												} else {
													if (zoneErrorMsg != null)
														request.setAttribute(CIRCLE_ERROR_MESSAGE, zoneErrorMsg);
													if (regionErrorMsg != null)
														request.setAttribute(CIRCLE_ERROR_MESSAGE, regionErrorMsg);
													forwardPage = ADD_CIRCLE;
												}
											} else {
												log.info("hublist size is 0");
												request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Zone must have atleast one Hub");
												forwardPage = ADD_CIRCLE;
											}
										} else {
											log.info("hubslit is null");
											request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Zone must have atleast one Hub");
											forwardPage = ADD_CIRCLE;
										}
									} else {
										log.info("zoneslist size is 0");
										request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Region must have atleast one Zone");
										forwardPage = ADD_CIRCLE;
									}
								} else {
									log.info("zoneslist size is null");
									request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Region must have atleast one Zone");
									forwardPage = ADD_CIRCLE;
								}
							} else {
								log.info("regionslist size is 0");
								request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Circle must have atleast one Region");
								forwardPage = ADD_CIRCLE;
							}
						} else {
							log.info("regionslist is null");
							request.setAttribute(CIRCLE_ERROR_MESSAGE, "Every Circle must have atleast one Region");
							forwardPage = ADD_CIRCLE;
						}
					} else {
						log.info("circles is null");
					}

				} else {
					log.info("cirlcemap is null");
					forwardPage = MANAGE_CIRCLES_LIST;
				}
			} else {
				log.info("session is null");
			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward saveCircles");
			log.error(e);
			// ActionMessages errors = new ActionMessages();
			// saveErrors(request,errors);
			// throw e;
		}
		log.info("END of the ActionForward saveCircles");
		return mapping.findForward(forwardPage);
	}

	// ===================================================================

	public ActionForward showManageCirclesPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward showManageCirclesPage");

		try {

		} catch (Exception e) {
			log.error("Problem in the ActionForward showManageCirclesPage");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward showManageCirclesPage");
		return mapping.findForward(Constants.MANAGE_CIRCLES_LIST);
	}

	// =========================================================================

	/**
	 * getCirclesDetails method is used for displaying the manage circles
	 * page.Retrive the details of circles ,regions,zones,hubs and construct the
	 * json and retrieve these values and display in the manage circles page
	 */
	public void getCirclesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward getCirclesDetails");
		CirclesManager circlesManager = null;
		List<CircleDetails> circlesList = null;
		HttpSession session = null;
		Long userId = null;
		Long roleId = null;

		try {
			session = request.getSession(false);
			userId = (Long) session.getAttribute(USER_ID);
			roleId = (Long) session.getAttribute(ROLE_ID);
			circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
			circlesList = new ArrayList<>();
			circlesList = circlesManager.getCircles(userId, roleId);
			org.json.JSONArray jsonArray = new org.json.JSONArray();
			
			if (circlesList != null) {
				for (int i = 0; i < circlesList.size(); i++) {
					org.json.JSONObject object = new org.json.JSONObject();
					String[] sArray = circlesList.get(i).getManagers();
					String managersName = null;
					StringBuilder builder = new StringBuilder();
					if (sArray != null) {
						for (int j = 0; j < sArray.length; j++) {
						  builder.append(sArray[j]);
						  builder.append(",");
							/*for (String s : sArray) {
								builder.append(s);
								builder.append(",");
							}*/
						}
						managersName = builder.substring(0, builder.length() - 1);
					} else {
						managersName = "";
					}
					object.put("circleId", circlesList.get(i).getCircleId());
					object.put("circleName", circlesList.get(i).getCircleName());
					object.put("managerNames", managersName);
					object.put("regionsCount", circlesList.get(i).getRegionsCount());
					object.put("zonesCount", circlesList.get(i).getZonesCount());
					object.put("hubsCount", circlesList.get(i).getHubsCount());
					jsonArray.put(object);
				}
				
			}
			response.setContentType("application/json");
			response.getWriter().print(jsonArray);
		} catch (Exception e) {
			log.error("Problem in the ActionForward getCirclesDetails");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward getCirclesDetails");
	}

	// ==========================================================================================

	/**
	 * 
	 * deleteCirclesDetails is used to delete the circles including its
	 * regions,zones and hubs also i.e set the status is INACTIVE
	 */

	public ActionForward deleteCirclesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward deleteCirclesDetails");

		CirclesManager circlesManager = null;
		String status = null;
		Long circleCode = Long.parseLong(request.getParameter("circleId"));
		try {
			circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);

			if (circleCode != null) {
				status = circlesManager.deleteCircle(circleCode);
			}
			if (status != null && status.equalsIgnoreCase(SUCCESS)) {
				request.setAttribute(SUCCESS_MESSAGE, DELETE_CIRCLE_SUCCESS_MESSAGE);
			}
			log.info("status is" + status);
		} catch (Exception e) {
			log.error("Problem in the ActionForward deleteCirclesDetails");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward deleteCirclesDetails");
		return mapping.findForward(Constants.MANAGE_CIRCLES_LIST);
	}

	// =====================================================================================

	/**
	 * editCirclesPage method is used for retrieving the values of circles based
	 * on the circleid and set these values in the session and redirected to add
	 * circles page
	 * 
	 * 
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward editCirclesPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward EditCirclesPage");
		CirclesManager circlesManager = null;
		Long circleId = 0L;
		CircleDetails circleDetails = null;
		List<RegionDetails> regionsList = null;
		List<ZoneDetails> zonesList = null;
		List<HubDetails> hubsList = null;
		HttpSession session = null;
		Map circlesMap = null;
		CirclesForm circlesForm = null;

		try {
			circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
			circleId = Long.parseLong(request.getParameter("circleId"));
			session = request.getSession(false);

			if (circleId != 0L) {

				circlesMap = circlesManager.getCircleDetails(circleId);

				if (circlesMap != null) {
					circleDetails = (CircleDetails) circlesMap.get(CIRCLE_DETAILS);
					if (circleDetails != null) {
						regionsList = (List) circlesMap.get(REGIONS);

						if (regionsList != null && regionsList.size() > 0) {
							zonesList = (List) circlesMap.get(ZONES);
							if (zonesList != null && zonesList.size() > 0) {
								hubsList = (List) circlesMap.get(HUBS);
								if (hubsList != null && hubsList.size() > 0) {
									if (session != null) {
										circlesForm = (CirclesForm) form;
										circlesForm.setCircleName(circleDetails.getCircleName());
										circlesForm.setCircleCode(circleDetails.getCircleCode());

										session.setAttribute("circlesMap", circlesMap);
										session.setAttribute(REGIONS, regionsList);
										session.setAttribute(ZONES, zonesList);
										session.setAttribute(HUBS, hubsList);
										session.setAttribute(CIRCLE_ID, circleId);
										session.setAttribute(CIRCLE_NAME, circleDetails.getCircleName());
										session.setAttribute(VIEW_TYPE, EDIT);
									} else {
										log.info("session is null");
									}
								} else {
									log.info("hubslist is null");
								}
							} else {
								log.info("zones list is null");
							}
						} else {
							log.info("regions list is null");
						}
					} else {
						log.info("circles details is null");
					}

				} else {
					log.info("cirlcesmap is null");
				}

			} else {
				log.info("circle id is null");
			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward EditCirclesPage");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward EditCirclesPage");
		return mapping.findForward(Constants.ADD_CIRCLE);
	}

	// ========================================================================================

	public void isCircleNameExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward isCircleNameExists");
		CirclesManager circlesManager = null;
		boolean isCircleExist = false;
		try {
			if (request.getParameter("circleName") != null)
				circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
			isCircleExist = circlesManager.isCircleNameExists(request.getParameter("circleName"));
			response.setContentType("text/text;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(isCircleExist ? 1 : 0);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("Problem in the ActionForward isCircleNameExists");
			log.error(e);
			ActionMessages errors = new ActionMessages();
			saveErrors(request, errors);
			throw e;
		}
		log.info("END of the ActionForward isCircleNameExists");
		return;

	}

	// ========================================================================================
	public ActionForward isRegionNameExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward isRegionNameExists");

		log.info("END of the ActionForward isRegionNameExists");
		return mapping.findForward(Constants.ADD_CIRCLE);

	}

	// ========================================================================================
	public ActionForward isZoneNameExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward isZoneNameExists");

		log.info("END of the ActionForward isZoneNameExists");
		return mapping.findForward(Constants.ADD_CIRCLE);

	}

	// ========================================================================================

	public ActionForward isHubNameExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the ActionForward isZoneNameExists");

		log.info("END of the ActionForward isZoneNameExists");
		return mapping.findForward(Constants.ADD_CIRCLE);

	}

	// ========================================================================================

	private List<RegionDetails> getRegionsOfCircle(List<RegionDetails> regions, String circleCode) throws Exception {
		log.info("START of the method getRegionsOfCircle");
		List<RegionDetails> regionsOfCircle = null;

		try {
			if (regions != null && regions.size() > 0 && StringUtil.isNotNull(circleCode)) {
				regionsOfCircle = new ArrayList<RegionDetails>();
				for (RegionDetails regionDetails : regions) {
					if (regionDetails != null && regionDetails.getCircleDetails().getCircleCode().equals(circleCode)) {
						regionsOfCircle.add(regionDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method getRegionsOfCircle");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getRegionsOfCircle");
		return regionsOfCircle;
	}

	// ========================================================================================

	private List<ZoneDetails> getZonesOfRegion(List<ZoneDetails> zones, String regionCode) throws Exception {
		log.info("START of the method getZonesOfRegion");
		List<ZoneDetails> zonesOfRegion = null;

		try {
			if (zones != null && zones.size() > 0 && StringUtil.isNotNull(regionCode)) {
				zonesOfRegion = new ArrayList<ZoneDetails>();
				for (ZoneDetails zoneDetails : zones) {
					if (zoneDetails != null && zoneDetails.getRegionDetails().getRegionCode().equals(regionCode)) {
						zonesOfRegion.add(zoneDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method getZonesOfRegion");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getZonesOfRegion");
		return zonesOfRegion;
	}

	// ==========================================================================

	private List<HubDetails> getHubsOfZone(List<HubDetails> hubs, String zoneCode) throws Exception {
		log.info("START of the method getHubsOfZone");
		List<HubDetails> hubsOfZone = null;

		try {
			if (hubs != null && hubs.size() > 0 && StringUtil.isNotNull(zoneCode)) {
				hubsOfZone = new ArrayList<HubDetails>();
				for (HubDetails hubDetails : hubs) {
					if (hubDetails != null && hubDetails.getZoneDetails().getZoneCode().equals(zoneCode)) {
						hubsOfZone.add(hubDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method getHubsOfZone");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getHubsOfZone");
		return hubsOfZone;
	}

	// ==========================================================================
	public ActionForward getRegionCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getRegionCode");

		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String code = UniqueCodeGenerator.getInstance().getRegionCode();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("unique_region_code", code);
			out.println(jsonObject);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("PROBLEM in the method getRegionCode");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getRegionCode");
		return null;
	}

	// ==========================================================================

	public ActionForward getZoneCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getZoneCode");

		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String code = UniqueCodeGenerator.getInstance().getZoneCode();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("unique_zone_code", code);
			out.println(jsonObject);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("PROBLEM in the method getZoneCode");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getZoneCode");
		return null;
	}

	// ==========================================================================

	public ActionForward getHubCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getHubCode");

		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String code = UniqueCodeGenerator.getInstance().getHubCode();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("unique_hub_code", code);
			out.println(jsonObject);
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("PROBLEM in the method getHubCode");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getHubCode");
		return null;
	}

	// ==========================================================================
	@SuppressWarnings("unchecked")
	public ActionForward getOrgChartHubsList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getOrgChartHubsList");
		String requestData = null;
		JSONArray jsonArray;
		String zoneName = null;
		String zoneCode = null;
		HttpSession session = null;
		List<HubDetails> hubsOfZone = null;
		List<HubDetails> hubsList = null;
		try {
			session = request.getSession(false);
			requestData = request.getParameter("requestData");
			jsonArray = JSONArray.fromObject(requestData);
			if (jsonArray != null) {
				zoneName = jsonArray.getJSONObject(0).getString("zoneName");
				zoneCode = jsonArray.getJSONObject(0).getString("zoneCode");
				hubsList = (List<HubDetails>) session.getAttribute(HUBS);
				JSONArray hubsOfZoneArray = new JSONArray();
				if (hubsList != null && zoneCode != null) {
					hubsOfZone = getHubsOfZone(hubsList, zoneCode);
					if (hubsOfZone != null) {
						for (HubDetails hubDetails : hubsOfZone) {
							JSONArray hubArray = new JSONArray();
							hubArray.add(hubDetails.getHubName());
							hubArray.add(zoneName);
							hubArray.add(hubDetails.getHubName());
							hubsOfZoneArray.add(hubArray);
						}
					}
				}
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("hubsOfZone", hubsOfZoneArray);
				out.println(jsonObject);
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			log.error("PROBLEM in the method getOrgChartHubsList");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getOrgChartHubsList");
		return null;
	}

	// ==========================================================================
	@SuppressWarnings("unchecked")
	public ActionForward getOrgChartZonesList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getOrgChartZonesList");
		String requestData = null;
		JSONArray jsonArray;
		String regionName = null;
		String regionCode = null;
		HttpSession session = null;
		List<ZoneDetails> zonesOfRegion = null;
		List<ZoneDetails> zonesList = null;
		List<HubDetails> hubsOfZone = null;
		List<HubDetails> hubsList = null;
		try {
			session = request.getSession(false);
			requestData = request.getParameter("requestData");
			jsonArray = JSONArray.fromObject(requestData);
			if (jsonArray != null) {
				regionName = jsonArray.getJSONObject(0).getString("regionName");
				regionCode = jsonArray.getJSONObject(0).getString("regionCode");
				zonesList = (List<ZoneDetails>) session.getAttribute(ZONES);
				JSONArray zonesOfRegionArray = new JSONArray();
				if (zonesList != null && regionCode != null) {
					zonesOfRegion = getZonesOfRegion(zonesList, regionCode);
					if (zonesOfRegion != null) {
						for (ZoneDetails zoneDetails : zonesOfRegion) {
							JSONArray zoneArray = new JSONArray();
							zoneArray.add(zoneDetails.getZoneName());
							zoneArray.add(regionName);
							zoneArray.add(zoneDetails.getZoneName());
							String zoneCode = zoneDetails.getZoneCode();
							String zoneName = zoneDetails.getZoneName();
							hubsList = (List<HubDetails>) session.getAttribute(HUBS);
							if (hubsList != null) {
								hubsOfZone = getHubsOfZone(hubsList, zoneCode);
								if (hubsOfZone != null) {
									for (HubDetails hubDetails : hubsOfZone) {
										JSONArray hubArray = new JSONArray();
										hubArray.add(hubDetails.getHubName());
										hubArray.add(zoneName);
										hubArray.add(hubDetails.getHubName());
										zonesOfRegionArray.add(hubArray);
									}
								}
							}
							zonesOfRegionArray.add(zoneArray);
						}
					}
				}
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("zonesOfRegion", zonesOfRegionArray);
				out.println(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method getOrgChartZonesList");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getOrgChartZonesList");
		return null;
	}

	// ==========================================================================
	@SuppressWarnings("unchecked")
	public ActionForward getOrgChartCircleList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method getOrgChartHubsList");
		String requestData = null;
		JSONArray jsonArray;
		String circleName = null;
		String circleCode = null;
		HttpSession session = null;
		List<RegionDetails> regionsOfCircle = null;
		List<RegionDetails> regionsList = null;
		List<ZoneDetails> zonesOfRegion = null;
		List<ZoneDetails> zonesList = null;
		List<HubDetails> hubsOfZone = null;
		List<HubDetails> hubsList = null;
		try {
			session = request.getSession(false);
			requestData = request.getParameter("requestData");
			jsonArray = JSONArray.fromObject(requestData);
			if (jsonArray != null) {
				circleName = jsonArray.getJSONObject(0).getString("circleName");
				circleCode = jsonArray.getJSONObject(0).getString("circleCode");
				regionsList = (List<RegionDetails>) session.getAttribute(REGIONS);
				JSONArray regionsOfCircleArray = new JSONArray();
				if (regionsList != null && circleCode != null) {
					regionsOfCircle = getRegionsOfCircle(regionsList, circleCode);
					if (regionsOfCircle != null) {
						for (RegionDetails regionDetails : regionsOfCircle) {
							JSONArray regionArray = new JSONArray();
							regionArray.add(regionDetails.getRegionName());
							regionArray.add(circleName);
							regionArray.add(regionDetails.getRegionName());
							String regionName = regionDetails.getRegionName();
							String regionCode = regionDetails.getRegionCode();
							zonesList = (List<ZoneDetails>) session.getAttribute(ZONES);
							if (zonesList != null && regionCode != null) {
								zonesOfRegion = getZonesOfRegion(zonesList, regionCode);
								if (zonesOfRegion != null) {
									for (ZoneDetails zoneDetails : zonesOfRegion) {
										JSONArray zoneArray = new JSONArray();
										zoneArray.add(zoneDetails.getZoneName());
										zoneArray.add(regionName);
										zoneArray.add(zoneDetails.getZoneName());
										String zoneCode = zoneDetails.getZoneCode();
										String zoneName = zoneDetails.getZoneName();
										hubsList = (List<HubDetails>) session.getAttribute(HUBS);
										if (hubsList != null) {
											hubsOfZone = getHubsOfZone(hubsList, zoneCode);
											if (hubsOfZone != null) {
												for (HubDetails hubDetails : hubsOfZone) {
													JSONArray hubArray = new JSONArray();
													hubArray.add(hubDetails.getHubName());
													hubArray.add(zoneName);
													hubArray.add(hubDetails.getHubName());
													regionsOfCircleArray.add(hubArray);
												}
											}
										}
										regionsOfCircleArray.add(zoneArray);
									}
								}
							}
							regionsOfCircleArray.add(regionArray);
						}
					}
				}
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("regionsOfCircle", regionsOfCircleArray);
				out.println(jsonObject);
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			log.error("PROBLEM in the method getOrgChartHubsList");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method getOrgChartHubsList");
		return null;
	}

	// ============================================================================================

	public void isRegionDeletable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method isRegionDeletable");
		CirclesManager circlesManager = null;
		HttpSession session = null;
		try {
			session = request.getSession(false);
			if (session != null) {
				circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
				String result = circlesManager.isRegionDeletable(Long.parseLong(request.getParameter("regionId")));
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("isRegionDeletable", result);
				out.println(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method isRegionDeletable");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method isRegionDeletable");
	}

	// ============================================================================================

	public void isZoneDeletable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("START of the method isZoneDeletable");
		CirclesManager circlesManager = null;
		HttpSession session = null;
		try {
			session = request.getSession(false);
			if (session != null) {
				circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
				String result = circlesManager.isZoneDeletable(Long.parseLong(request.getParameter("zoneId")));
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("isZoneDeletable", result);
				out.println(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method isZoneDeletable");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method isZoneDeletable");
	}

	// ============================================================================================

	public void isHubDeletable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("START of the method isHubDeletable");
		CirclesManager circlesManager = null;
		HttpSession session = null;
		try {
			session = request.getSession(false);
			if (session != null) {
				circlesManager = (CirclesManager) getBean(CIRCLES_MANAGER);
				String result = circlesManager.isHubDeletable(Long.parseLong(request.getParameter("hubId")));
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("isHubDeletable", result);
				out.println(jsonObject);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			log.error("PROBLEM in the method isHubDeletable");
			log.error(e);
			e.printStackTrace();
		}
		log.info("END of the method isHubDeletable");
	}
}