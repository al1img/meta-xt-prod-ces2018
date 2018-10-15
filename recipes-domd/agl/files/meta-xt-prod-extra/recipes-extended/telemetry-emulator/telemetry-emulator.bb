SUMMARY = "telemetry emulator for CES2018 demo"
DESCRIPTION = "config files and scripts for guest which will be running for tests"

require inc/xt_shared_env.inc

DIR_TELEMETRY_EMULATOR = "${XT_DIR_ABS_ROOTFS}/telemetry_emulator"

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRCREV = "${AUTOREV}"
SRC_URI = " \
    git://git@gitpct.epam.com/epmd-aepr/demo_insurance;protocol=ssh;branch=develop \
    file://telemetry-emulator.service \
"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = " \
    python3 \
    python3-argparse \
    python3-compression \
    python3-datetime \
    python3-json \
    python3-misc \
    python3-netserver \
    python3-selectors \
    python3-shell \
    python3-signal \
    python3-textutils \
    python3-threading \
"

inherit systemd

SYSTEMD_PACKAGES = " \
    ${PN} \
"

SYSTEMD_SERVICE_${PN} = "telemetry-emulator.service"

FILES_${PN} = " \
    ${systemd_system_unitdir}/telemetry-emulator.service \
    ${base_prefix}${DIR_TELEMETRY_EMULATOR}/*.py \
    ${base_prefix}${DIR_TELEMETRY_EMULATOR}/*.json \
"

do_install() {
    install -d ${D}${base_prefix}${DIR_TELEMETRY_EMULATOR}
    install -m 0644 ${S}/backend/project/apps/telemetry_emulator/*.py ${D}${base_prefix}${DIR_TELEMETRY_EMULATOR}
    install -m 0644 ${S}/backend/project/apps/telemetry_emulator/*.json ${D}${base_prefix}${DIR_TELEMETRY_EMULATOR}

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/*.service ${D}${systemd_system_unitdir}
}
