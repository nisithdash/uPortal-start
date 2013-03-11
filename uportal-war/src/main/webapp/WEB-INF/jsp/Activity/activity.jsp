<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="n"><portlet:namespace/></c:set>

<!--
PORTLET DEVELOPMENT STANDARDS AND GUIDELINES
| For the standards and guidelines that govern
| the user interface of this portlet
| including HTML, CSS, JavaScript, accessibilty,
| naming conventions, 3rd Party libraries
| (like jQuery and the Fluid Skinning System)
| and more, refer to:
| http://www.ja-sig.org/wiki/x/cQ
-->

<style type="text/css">
    .fl-col {
        width:200px;
        height:150px;
        background-color: #2E2E2E;
    }

    .fl-col .box-header {
        width:100%;
        height:20px;
        background-color:black;
        text-align:center;
        vertical-align: middle;
        font-weight:bold;
        color:white;
    }

    .box-total {
        color:white;
        font-weight:bold;
        font-size:xx-large;
        text-align:center;
    }

    .box-data {
        margin-top:15px;
        color:white;
    }

    .box-data TABLE {
        margin-left: auto;
        margin-right:auto;
        border-collapse: collapse;
    }

    .box-data TABLE td {
        line-height: .3em;
    }

    .popular-search {
        clear:both;
        margin-top:15px;
    }

    .popular-search .title {
        margin-top:15px;
        font-weight:bold;
    }

    a:link,a:visited,a:hover,a:active {
        color: inherit;
        text-decoration: none;
    }
</style>

<!-- Portlet -->
<div class="fl-widget portlet" role="section">
    <form id="${n}form">

        <!-- Portlet Body -->
        <div class="fl-widget-content portlet-body" role="main">

            <!-- Portlet Section -->
            <div id="${n}popularPortlets" class="portlet-section fl-pager" role="region">

                <div class="portlet-section-body">
                    <span style="font-weight:bold;"><spring:message code="portal.activity.who"/></span>
                    <br/><br/>
                    <div class="fl-container-flex fl-centered">
                        <div class="fl-container-flex fl-col-flex5 fl-fix content">
                            <div class="fl-col">
                                <div class="box-header"><spring:message code="portal.activity.now"/></div>
                                <div class="box-total">${usageNow.total}</div>
                                <div class="box-data">
                                    <table cellpadding="0" cellspacing="0">
                                        <c:forEach items="${usageNow.groupActivity}" var="groupActivity">
                                            <tr>
                                                <td style="text-align:right;">${groupActivity.groupName}</td>
                                                <td style="text-align:left;">${groupActivity.total}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <div class="fl-col">
                                <div class="box-header"><spring:message code="portal.activity.today"/></div>
                                <div class="box-total">${usageToday.total}</div>
                                <div class="box-data">
                                    <table cellpadding="0" cellspacing="0">
                                        <c:forEach items="${usageToday.groupActivity}" var="groupActivity">
                                            <tr>
                                                <td style="text-align:right;">${groupActivity.groupName}</td>
                                                <td style="text-align:left;">${groupActivity.total}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <div class="fl-col">
                                <div class="box-header"><spring:message code="portal.activity.yesterday"/></div>
                                <div class="box-total">${usageYesterday.total}</div>
                                <div class="box-data">
                                    <table cellpadding="0" cellspacing="0">
                                        <c:forEach items="${usageYesterday.groupActivity}" var="groupActivity">
                                            <tr>
                                                <td style="text-align:right;">${groupActivity.groupName}</td>
                                                <td style="text-align:left;">${groupActivity.total}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="popular-search">
                        <div class="title"><spring:message code="portal.activity.searching"/></div>
                        <div class="results">
                            <c:forEach items="${popularSearchTerms}" var="searchInfo" varStatus="status">
                                <c:if test="${status.index > 0}"><bold>|</bold></c:if>
                                <a href="${renderRequest.contextPath}/p/search/max/action.uP?pP_query=${searchInfo.searchTerm}">${searchInfo.searchTerm}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
