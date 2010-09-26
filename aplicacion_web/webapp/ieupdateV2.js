function getChart(chart, url, width, height){	
	document.write('<EMBED\n');				
    document.write('	src=\"' + chart + '"\n');
    document.write('	FlashVars=\"&' + url + '&chartWidth='+ width +'&chartHeight=' + height +'\"\n');
    document.write('	quality=high\n');
    document.write('	bgcolor=#FFFFFF\n');
    document.write('	WIDTH=\"'+ width +'\"\n');
    document.write('	HEIGHT=\"' + height +'\"\n');
    document.write('	NAME=\"FCPie\"\n');
    document.write('	TYPE=\"application/x-shockwave-flash\"\n');
    document.write('	PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\">\n');
	document.write('</EMBED>\n');	
}

function getChartFull(chart, url, width, height, quality, bgcolor){
	document.write('<EMBED\n');				
    document.write('	src=\"' + chart + '"\n');
    document.write('	FlashVars=\"&' + url + '&chartWidth='+ width +'&chartHeight=' + height +'\"\n');
    document.write('	quality=' + quality + '\n');
    document.write('	bgcolor=#' + bgcolor + '\n');
    document.write('	WIDTH=\"'+ width +'\"\n');
    document.write('	HEIGHT=\"' + height +'\"\n');
    document.write('	NAME=\"FCPie\"\n');
    document.write('	TYPE=\"application/x-shockwave-flash\"\n');
    document.write('	PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\">\n');
	document.write('</EMBED>\n');	
}

function getLegend(chart, url, width, height, rowBgColor){
	document.write('<EMBED\n');				
    document.write('	src=\"' + chart + '?'+ url +'&chartHeight='+ height +'&chartWidth='+ width +'\"\n');
    document.write('	FlashVars=\"&alternateRowBgColor='+ rowBgColor +'&alternateRowBgAlpha=10\"\n');
    document.write('	quality=high\n');
    document.write('	bgcolor=#FFFFFF\n');
    document.write('	WIDTH=\"'+ width +'\"\n');
    document.write('	HEIGHT=\"' + height +'\"\n');
    document.write('	NAME=\"FCGrid\"\n');
    document.write('	TYPE=\"application/x-shockwave-flash\"\n');
    document.write('	PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\">\n');
	document.write('</EMBED>\n');	
}