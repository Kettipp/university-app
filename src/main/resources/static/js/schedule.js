const groupForm = document.forms['selectGroups'];
const teacherForm = document.forms['selectTeachers'];
const selectGroup = groupForm.querySelector("#groups");
const selectTeacher = teacherForm.querySelector("#teachers");
function choiceGroup() {
    const selectedValue = selectGroup.value;
    window.location.replace("/university/schedule/group/" + selectedValue);
}
function choiceTeacher() {
    const selectedValue = selectTeacher.value;
    const split = selectedValue.toString().split(' ');
    window.location.replace("/university/schedule/teacher/" + split[0] + "/" + split[1]);
}

selectGroup.addEventListener("change", choiceGroup);
selectTeacher.addEventListener("change", choiceTeacher);