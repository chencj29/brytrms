/**
 * Created by xiaowu on 15/12/11.
 */
var overlaps = (function () {
    function getPositions (elem) {
        var pos, width, height;
        pos = $(elem).position();
        width = $(elem).width();
        height = $(elem).height();
        return [[pos.left, pos.left + width], [pos.top, pos.top + height]];
    }

    function comparePositions (p1, p2) {
        var r1, r2;
        r1 = p1[0] < p2[0] ? p1 : p2;
        r2 = p1[0] < p2[0] ? p2 : p1;
        return r1[1] > r2[0] || r1[0] === r2[0];
    }

    return function (a, b, threshold) {
        var pos1 = getPositions(a), pos2 = getPositions(b);
        var normal_result = comparePositions(pos1[0], pos2[0]) && comparePositions(pos1[1], pos2[1]);


        if (threshold === undefined) {
            return normal_result
        } else {

            //如果不存在碰撞,则加入额外条件进行检测
            //判断是否在同一高度
            var gateA = a.data('o').gate, gateB = b.data('o').gate;

            //检测单一机位的航班
            var intersectionArray = _.intersection(gateA, gateB);

            if (_.isEmpty(intersectionArray) && gateA[0].text == '预留区' && gateB[0].text == '预留区') return normal_result;

            if (pos1[1][0] === pos2[1][0] || !_.isEmpty(intersectionArray)) {
                var r1, r2;
                r1 = pos1[0][1] > pos2[0][1] ? pos2 : pos1;
                r2 = pos1[0][1] > pos2[0][1] ? pos1 : pos2;
                return Math.ceil(r2[0][0] - r1[0][1]) < threshold;
            } else return false;


        }
    };
})();