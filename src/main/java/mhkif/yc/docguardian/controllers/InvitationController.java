package mhkif.yc.docguardian.controllers;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.services.InvitationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doc_guardian/api/v1/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService service;

}
