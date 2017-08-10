<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/base.jsp" %> 
<script>
var basePath='${basePath}';
</script>
<script src="${basePath}static/js/cron.js" type="text/javascript"></script>
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>秒</span></a></li>
					<li><a href="javascript:;"><span>分钟</span></a></li>
					<li><a href="javascript:;"><span>小时</span></a></li>
					<li><a href="javascript:;"><span>日</span></a></li>
					<li><a href="javascript:;"><span>月</span></a></li>
					<li><a href="javascript:;"><span>周</span></a></li>
					<li><a href="javascript:;"><span>年</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:200px;">
                    <div title="秒">
                        <div class="line">
                            <input type="radio" checked="checked" name="second" onclick="everyTime(this)">每秒 允许的通配符[, - * /]</div>
                        <div class="line">
                            <input type="radio" name="second" onclick="cycle(this)">周期从
                           <select id="secondStart_0" name="secondStart_0"  class="numberspinner">
                       			<c:forEach var ="i" begin="1" end= "58">
                       				<option name="">${i }</option>
								</c:forEach>
                           </select>
                            -
                       		<select id="secondEnd_0" name="secondEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                       </div> 
                       <div class="line">
                            <input type="radio" name="second" onclick="startOn(this)"> 从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:0,max:59" value="0"
                                id="secondStart_1">   -->
                             <select id="secondStart_1" name="secondStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="0" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>秒开始,每
                           <!--  <input class="numberspinner" style="width: 60px;" data-options="min:1,max:59" value="1"
                                id="secondEnd_1"> -->
                             <select id="secondEnd_1" name="secondEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>秒执行一次
                       </div>
                       <div class="line">
                            <input type="radio" name="second" id="sencond_appoint">指定</div>
                       <div class="imp secondList">
                        	<c:forEach var ="i" begin="0" end= "9">
                          			<input type="checkbox" value="${i }">0${i }
							</c:forEach>
                        </div>
                        <div class="imp secondList">
                        	<c:forEach var ="i" begin="10" end= "19">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp secondList">
                            <c:forEach var ="i" begin="20" end= "29">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp secondList">
                            <c:forEach var ="i" begin="30" end= "39">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp secondList">
                            <c:forEach var ="i" begin="40" end= "49">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp secondList">
                            <c:forEach var ="i" begin="50" end= "59">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="分钟">
                        <div class="line">
                            <input type="radio" checked="checked" name="min" onclick="everyTime(this)">分钟 允许的通配符[, - * /]</div>
                        <div class="line">
                            <input type="radio" name="min" onclick="cycle(this)">周期从
                           <!--  <input class="numberspinner" style="width: 60px;" data-options="min:1,max:58" value="1"
                                id="minStart_0"> -->
                            <select id="minStart_0" name="minStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "58">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>   
                            -
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:2,max:59" value="2"
                                id="minEnd_0"> -->
                            <select id="minEnd_0" name="minEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>分钟</div>
                        <div class="line">
                            <input type="radio" name="min" onclick="startOn(this)">从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:0,max:59" value="0"
                                id="minStart_1"> -->
                             <select id="minStart_1" name="minStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="0" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>分钟开始,每
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:59" value="1"
                                id="minEnd_1"> -->
                             <select id="minEnd_1" name="minEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "59">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>分钟执行一次</div>
                        <div class="line">
                            <input type="radio" name="min" id="min_appoint">指定</div>
                        <div class="imp minList">
                        	<c:forEach var ="i" begin="0" end= "9">
                          			<input type="checkbox" value="${i }">0${i }
							</c:forEach>
                        </div>
                        <div class="imp minList">
                        	<c:forEach var ="i" begin="10" end= "19">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp minList">
                            <c:forEach var ="i" begin="20" end= "29">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp minList">
                            <c:forEach var ="i" begin="30" end= "39">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp minList">
                            <c:forEach var ="i" begin="40" end= "49">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp minList">
                            <c:forEach var ="i" begin="50" end= "59">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="小时">
                        <div class="line">
                            <input type="radio" checked="checked" name="hour" onclick="everyTime(this)">小时 允许的通配符[, - * /]</div>
                        <div class="line">
                            <input type="radio" name="hour" onclick="cycle(this)">周期从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:0,max:23" value="0"
                                id="hourStart_0"> -->
                            <select id="hourStart_0" name="hourStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="0" end= "23">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                            -
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:2,max:23" value="2"
                                id="hourEnd_1"> -->
                             <select id="hourEnd_1" name="hourEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "23">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>小时</div>
                        <div class="line">
                            <input type="radio" name="hour" onclick="startOn(this)">从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:0,max:23" value="0"
                                id="hourStart_1"> -->
                             <select id="hourStart_1" name="hourStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="2" end= "23">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>小时开始,每
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:23" value="1"
                                id="hourEnd_1"> -->
                             <select id="hourEnd_1" name="hourEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "23">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>小时执行一次</div>
                        <div class="line">
                            <input type="radio" name="hour" id="hour_appoint">指定</div>
                        <div class="imp hourList">
                            AM:
                            <c:forEach var ="i" begin="00" end= "11">
                          			<input type="checkbox" value="${i }">0${i }
							</c:forEach>
                        </div>
                        <div class="imp hourList">
                            PM:
                            <c:forEach var ="i" begin="12" end= "23">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="日">
                        <div class="line">
                            <input type="radio" checked="checked" name="day" onclick="everyTime(this)">日 允许的通配符[, - * / L W]</div>
                        <div class="line">
                            <input type="radio" name="day" onclick="unAppoint(this)">不指定</div>
                        <div class="line">
                            <input type="radio" name="day" onclick="cycle(this)">周期从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:31" value="1"
                                id="dayStart_0"> -->
                            <select id="dayStart_0" name="dayStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "31">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                            -
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:2,max:31" value="2"
                                id="dayEnd_0"> -->
                           <select id="dayEnd_0" name="dayEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "31">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>日</div>
                        <div class="line">
                            <input type="radio" name="day" onclick="startOn(this)">从
                           <!--  <input class="numberspinner" style="width: 60px;" data-options="min:1,max:31" value="1"
                                id="dayStart_1"> -->
                           <select id="dayStart_1" name="dayStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "31">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>日开始,每
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:31" value="1"
                                id="dayEnd_1"> -->
                            <select id="dayEnd_1" name="dayEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "31">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>天执行一次</div>
                        <div class="line">
                            <input type="radio" name="day" onclick="workDay(this)">每月
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:31" value="1"
                                id="dayStart_2"> -->
                            <select id="dayStart_2" name="dayStart_2" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "31">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>号最近的那个工作日</div>
                        <div class="line">
                            <input type="radio" name="day" onclick="lastDay(this)">本月最后一天</div>
                        <div class="line">
                            <input type="radio" name="day" id="day_appoint">指定</div>
                        <div class="imp dayList">
                           <c:forEach var ="i" begin="1" end= "16">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                        <div class="imp dayList">
                            <c:forEach var ="i" begin="17" end= "31">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="月">
                        <div class="line">
                            <input type="radio" checked="checked" name="mouth" onclick="everyTime(this)">月 允许的通配符[, - * /]</div>
                        <div class="line">
                            <input type="radio" name="mouth" onclick="unAppoint(this)">不指定</div>
                        <div class="line">
                            <input type="radio" name="mouth" onclick="cycle(this)">周期从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:12" value="1"
                                id="mouthStart_0"> -->
                            <select id="mouthStart_0" name="mouthStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "12">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                            -
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:2,max:12" value="2"
                                id="mouthEnd_0"> -->
                            <select id="mouthEnd_0" name="mouthEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2" end= "12">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>月</div>
                        <div class="line">
                            <input type="radio" name="mouth" onclick="startOn(this)">从
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:12" value="1"
                                id="mouthStart_1"> -->
                                <select id="mouthStart_1" name="mouthStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "12">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>日开始,每
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:12" value="1"
                                id="mouthEnd_1"> -->
                            <select id="mouthEnd_1" name="mouthEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "12">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>月执行一次</div>
                        <div class="line">
                            <input type="radio" name="mouth" id="mouth_appoint">指定</div>
                        <div class="imp mouthList">
                            <c:forEach var ="i" begin="1" end= "12">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="周">
                        <div class="line">
                            <input type="radio" checked="checked" name="week" onclick="everyTime(this)">周 允许的通配符[, - * / L #]</div>
                        <div class="line">
                            <input type="radio" name="week" onclick="unAppoint(this)">不指定</div>
                        <div class="line">
                            <!-- <input type="radio" name="week" onclick="startOn(this)">周期 从星期<input class="numberspinner" style="width: 60px;" data-options="min:1,max:7"
                                id="weekStart_0" value="1"> -->
                            <select id="weekStart_0" name="weekStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "7">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                            -
                            <!-- <input class="numberspinner" style="width: 60px;" data-options="min:2,max:7" value="2"
                                id="weekEnd_0"> -->
                           <select id="weekEnd_0" name="weekEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2" end= "7">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select></div>
                        <div class="line">
                            <input type="radio" name="week" onclick="weekOfDay(this)">第<!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:4" value="1"
                                id="weekStart_1"> -->
                              <select id="weekStart_1" name="weekStart_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "4">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>  
                                
                                周 的星期<!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:7"
                                id="weekEnd_1" value="1"> -->
                            <select id="weekEnd_1" name="weekEnd_1" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "7">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                                
                                </div>
                        <div class="line">
                            <input type="radio" name="week" onclick="lastWeek(this)">本月最后一个星期<!-- <input class="numberspinner" style="width: 60px;" data-options="min:1,max:7"
                                id="weekStart_2" value="1"> -->
                            <select id="weekStart_2" name="weekStart_2" class="numberspinner">
                          		<c:forEach var ="i" begin="1" end= "7">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select></div>
                        <div class="line">
                            <input type="radio" name="week" id="week_appoint">指定</div>
                        <div class="imp weekList">
                            <c:forEach var ="i" begin="1" end= "7">
                          			<input type="checkbox" value="${i }">${i }
							</c:forEach>
                        </div>
                    </div>
                    <div title="年">
                        <div class="line">
                            <input type="radio" checked="checked" name="year" onclick="unAppoint(this)">不指定 允许的通配符[, - * /] 非必填</div>
                        <div class="line">
                            <input type="radio" name="year" onclick="everyTime(this)">每年</div>
                        <div class="line">
                            <input type="radio" name="year" onclick="cycle(this)">周期 从
                            <!-- <input class="numberspinner" style="width: 90px;" data-options="min:2013,max:3000"
                                id="yearStart_0" value="2013"> -->
                            <select id="yearStart_0" name="yearStart_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2013" end= "2030">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select>
                            -
                            <!-- <input class="numberspinner" style="width: 90px;" data-options="min:2014,max:3000"
                                id="yearEnd_0" value="2014"> -->
                            <select id="yearEnd_0" name="yearEnd_0" class="numberspinner">
                          		<c:forEach var ="i" begin="2014" end= "2030">
                          			<option name="">${i }</option>
								</c:forEach>
                           	</select></div>
                    </div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	<div class="panel" style="margin: 0px;">
			<div class="panelHeader"><div class="panelHeaderContent"><h1>表达式</h1></div></div>
			<div class="panelContent" style="height: 240px;">
                <table>
                        <tbody>
                            <tr>
                                <td></td>
                                <td align="center">秒</td>
                                <td align="center">分钟</td>
                                <td align="center">小时</td>
                                <td align="center">日</td>
                                <td align="center">月<br/></td>
                                <td align="center">星期</td>
                                <td align="center">年</td>
                            </tr>
                            <tr>
                                <td>表达式字段:</td>
                                <td>
                                    <input type="text" name="v_second"  value="*" readonly="readonly"  style="width:50px;"/>
                                </td>
                                <td>
                                    <input type="text" name="v_min"  value="*" readonly="readonly"  style="width:50px;" />
                                </td>
                                <td>
                                    <input type="text" name="v_hour"  value="*" readonly="readonly"  style="width:50px;" />
                                </td>
                                <td>
                                    <input type="text" name="v_day"  value="*" readonly="readonly"  style="width:50px;"/>
                                </td>
                                <td>
                                    <input type="text" name="v_mouth"  value="*" readonly="readonly"  style="width:50px;"/>
                                </td>
                                <td>
                                    <input type="text" name="v_week"  value="?" readonly="readonly"  style="width:50px;"/>
                                </td>
                                <td>
                                    <input type="text" name="v_year"  readonly="readonly"  style="width:50px;"/>
                                </td>
                            </tr>
                             <tr>
							    <td>Cron 表达式:</td>
							    <td colspan="7"><input type="text" name="cron" value="* * * * * ?" id="cron"
                                         /></td>
						    </tr>
                            <tr>
							    <td colspan="8" >最近5次运行时间:</td>
						    </tr>
                            <tr>
                                <td colspan="8" id="runTime">
                                </td>
                            </tr>
                        </tbody>
                    </table>
			</div>
	</div>
	<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close" onclick="makeSurePeriod();">确定</button></div></div>
				</li>
			</ul>
		</div>
</div>
<script type="text/javascript">
                        /*killIe*/
                     //   $.parser.parse($("body"));
                        var cpro_id = "u1331261";
                        function btnFan() {
                            //获取参数中表达式的值
                            var txt = $("#cron").val();
                            if (txt) {
                                var regs = txt.split(' ');
                                $("input[name=v_second]").val(regs[0]);
                                $("input[name=v_min]").val(regs[1]);
                                $("input[name=v_hour]").val(regs[2]);
                                $("input[name=v_day]").val(regs[3]);
                                $("input[name=v_mouth]").val(regs[4]);
                                $("input[name=v_week]").val(regs[5]);

                                initObj(regs[0], "second");
                                initObj(regs[1], "min");
                                initObj(regs[2], "hour");
                                initDay(regs[3]);
                                initMonth(regs[4]);
                                initWeek(regs[5]);

                                if (regs.length > 6) {
                                    $("input[name=v_year]").val(regs[6]);
                                    initYear(regs[6]);
                                }
                            }
                        }
                      

                        function initObj(strVal, strid) {
                            var ary = null;
                            var objRadio = $("input[name='" + strid + "'");
                            if (strVal == "*") {
                                objRadio.eq(0).attr("checked", "checked");
                            } else if (strVal.split('-').length > 1) {
                                ary = strVal.split('-');
                                objRadio.eq(1).attr("checked", "checked");
                            } else if (strVal.split('/').length > 1) {
                                ary = strVal.split('/');
                                objRadio.eq(2).attr("checked", "checked");
                            } else {
                                objRadio.eq(3).attr("checked", "checked");
                                if (strVal != "?") {
                                    ary = strVal.split(",");
                                    for (var i = 0; i < ary.length; i++) {
                                        $("." + strid + "List input[value='" + ary[i] + "']").attr("checked", "checked");
                                    }
                                }
                            }
                        }

                        function initDay(strVal) {
                            var ary = null;
                            var objRadio = $("input[name='day'");
                            if (strVal == "*") {
                                objRadio.eq(0).attr("checked", "checked");
                            } else if (strVal == "?") {
                                objRadio.eq(1).attr("checked", "checked");
                            } else if (strVal.split('-').length > 1) {
                                ary = strVal.split('-');
                                objRadio.eq(2).attr("checked", "checked");
                            } else if (strVal.split('/').length > 1) {
                                ary = strVal.split('/');
                                objRadio.eq(3).attr("checked", "checked");
                            } else if (strVal.split('W').length > 1) {
                                ary = strVal.split('W');
                                objRadio.eq(4).attr("checked", "checked");
                            } else if (strVal == "L") {
                                objRadio.eq(5).attr("checked", "checked");
                            } else {
                                objRadio.eq(6).attr("checked", "checked");
                                ary = strVal.split(",");
                                for (var i = 0; i < ary.length; i++) {
                                    $(".dayList input[value='" + ary[i] + "']").attr("checked", "checked");
                                }
                            }
                        }

                        function initMonth(strVal) {
                            var ary = null;
                            var objRadio = $("input[name='mouth'");
                            if (strVal == "*") {
                                objRadio.eq(0).attr("checked", "checked");
                            } else if (strVal == "?") {
                                objRadio.eq(1).attr("checked", "checked");
                            } else if (strVal.split('-').length > 1) {
                                ary = strVal.split('-');
                                objRadio.eq(2).attr("checked", "checked");
                            } else if (strVal.split('/').length > 1) {
                                ary = strVal.split('/');
                                objRadio.eq(3).attr("checked", "checked");

                            } else {
                                objRadio.eq(4).attr("checked", "checked");

                                ary = strVal.split(",");
                                for (var i = 0; i < ary.length; i++) {
                                    $(".mouthList input[value='" + ary[i] + "']").attr("checked", "checked");
                                }
                            }
                        }

                        function initWeek(strVal) {
                            var ary = null;
                            var objRadio = $("input[name='week'");
                            if (strVal == "*") {
                                objRadio.eq(0).attr("checked", "checked");
                            } else if (strVal == "?") {
                                objRadio.eq(1).attr("checked", "checked");
                            } else if (strVal.split('/').length > 1) {
                                ary = strVal.split('/');
                                objRadio.eq(2).attr("checked", "checked");
                            } else if (strVal.split('-').length > 1) {
                                ary = strVal.split('-');
                                objRadio.eq(3).attr("checked", "checked");
                            } else if (strVal.split('L').length > 1) {
                                ary = strVal.split('L');
                                objRadio.eq(4).attr("checked", "checked");
                            } else {
                                objRadio.eq(5).attr("checked", "checked");
                                ary = strVal.split(",");
                                for (var i = 0; i < ary.length; i++) {
                                    $(".weekList input[value='" + ary[i] + "']").attr("checked", "checked");
                                }
                            }
                        }

                        function initYear(strVal) {
                            var ary = null;
                            var objRadio = $("input[name='year'");
                            if (strVal == "*") {
                                objRadio.eq(1).attr("checked", "checked");
                            } else if (strVal.split('-').length > 1) {
                                ary = strVal.split('-');
                                objRadio.eq(2).attr("checked", "checked");
                            }
                        }
                        
                        // 确定/关闭弹框，并将值带回父页面     裴习柱   2017-04-14
                        function makeSurePeriod(){
                        	$("#runPeriod").val($("#cron").val());
                        }

                    </script>